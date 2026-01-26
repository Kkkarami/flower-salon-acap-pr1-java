package fedelesh.flowersalon.infrastructure.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import fedelesh.flowersalon.domain.Entity;
import fedelesh.flowersalon.domain.specification.Specification;
import fedelesh.flowersalon.infrastructure.storage.exception.StorageException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public abstract class JsonRepository<T extends Entity> implements Repository<T> {

    protected final Path filePath;
    protected final Gson gson;
    protected final Type listType;

    protected final IdentityMap<T> identityMap = new IdentityMap<>();

    private boolean cacheValid = false;
    private List<T> cachedList = null;

    protected JsonRepository(String filename, Type listType) {
        this.filePath = Path.of(filename);
        this.listType = listType;
        this.gson = new GsonBuilder()
              .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
              .setPrettyPrinting()
              .serializeNulls()
              .create();

        ensureDirectoryExists();
    }

    @Override
    public T save(T entity) {
        UUID id = entity.getId();

        identityMap.put(id, entity);

        invalidateCache();

        List<T> entities = loadFromFile();

        boolean exists = entities.stream()
              .anyMatch(e -> e.getId().equals(id));

        if (exists) {
            entities.replaceAll(e -> e.getId().equals(id) ? entity : e);
        } else {
            entities.add(entity);
        }

        writeToFile(entities);
        return entity;
    }

    @Override
    public Optional<T> findById(UUID id) {
        return identityMap.get(id)
              .or(() -> findAllInternal().stream()
                    .filter(entity -> entity.getId().equals(id))
                    .findFirst());
    }

    @Override
    public List<T> findAll(Specification<T> spec) {
        List<T> all = findAllInternal();
        if (spec == null) {
            return all;
        }
        return all.stream().filter(spec::isSatisfiedBy).toList();
    }

    @Override
    public Optional<T> findOne(Specification<T> spec) {
        return findAllInternal().stream()
              .filter(spec::isSatisfiedBy)
              .findFirst();
    }

    @Override
    public long count(Specification<T> spec) {
        return findAllInternal().stream()
              .filter(spec::isSatisfiedBy)
              .count();
    }

    @Override
    public boolean exists(Specification<T> spec) {
        return findAllInternal().stream()
              .anyMatch(spec::isSatisfiedBy);
    }


    @Override
    public boolean deleteById(UUID id) {
        identityMap.remove(id);
        invalidateCache();

        List<T> entities = loadFromFile();
        boolean removed = entities.removeIf(entity ->
              entity.getId().equals(id)
        );

        if (removed) {
            writeToFile(entities);
        }
        return removed;
    }

    @Override
    public boolean delete(T entity) {
        return deleteById(entity.getId());
    }

    @Override
    public boolean existsById(UUID id) {
        return identityMap.contains(id) || findById(id).isPresent();
    }

    /**
     * Інвалідує кеш. Викликається при модифікації даних.
     */
    protected void invalidateCache() {
        cacheValid = false;
        cachedList = null;
    }

    /**
     * Повертає всі сутності з кешу або файлу.
     */
    protected List<T> findAllInternal() {
        if (cacheValid && cachedList != null) {
            return cachedList;
        }

        cachedList = loadFromFile();
        cacheValid = true;

        // Заповнюємо Identity Map
        cachedList.forEach(entity -> {
            if (!identityMap.contains(entity.getId())) {
                identityMap.put(entity.getId(), entity);
            }
        });

        return cachedList;
    }

    /**
     * Читає всі сутності з файлу.
     */
    private List<T> loadFromFile() {
        if (!Files.exists(filePath)) {
            return new ArrayList<>();
        }

        try (Reader reader = new FileReader(filePath.toFile())) {
            List<T> entities = gson.fromJson(reader, listType);
            return entities != null ? new ArrayList<>(entities) : new ArrayList<>();
        } catch (IOException e) {
            throw new StorageException("Помилка читання з файлу: " + filePath, e);
        }
    }

    /**
     * Записує всі сутності у файл.
     */
    protected void writeToFile(List<T> entities) {
        try (Writer writer = new FileWriter(filePath.toFile())) {
            gson.toJson(entities, writer);
        } catch (IOException e) {
            throw new StorageException("Помилка запису у файл: " + filePath, e);
        }
    }

    private void ensureDirectoryExists() {
        Path parent = filePath.getParent();
        if (parent != null && !Files.exists(parent)) {
            try {
                Files.createDirectories(parent);
            } catch (IOException e) {
                throw new StorageException("Не вдалося створити директорію: " + parent, e);
            }
        }
    }

    private static class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {

        private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        @Override
        public void write(JsonWriter out, LocalDateTime value) throws IOException {
            if (value == null) {
                out.nullValue();
            } else {
                out.value(value.format(formatter));
            }
        }

        @Override
        public LocalDateTime read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            return LocalDateTime.parse(in.nextString(), formatter);
        }
    }
}

