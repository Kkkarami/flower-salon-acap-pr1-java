package fedelesh.flowersalon.infrastructure.storage;

import fedelesh.flowersalon.domain.Entity;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Реалізація патерну Identity Map. Кешує завантажені сутності за їх ідентифікаторами.
 *
 * @param <T> тип сутності
 */
public class IdentityMap<T extends Entity> {

    private final Map<UUID, T> cache = new HashMap<>();

    /**
     * Отримує сутність з кешу.
     *
     * @param id ідентифікатор
     * @return Optional з сутністю або порожній Optional
     */
    public Optional<T> get(UUID id) {
        return Optional.ofNullable(cache.get(id));
    }

    /**
     * Додає сутність до кешу.
     *
     * @param id     ідентифікатор
     * @param entity сутність
     */
    public void put(UUID id, T entity) {
        cache.put(id, entity);
    }

    /**
     * Видаляє сутність з кешу.
     *
     * @param id ідентифікатор
     */
    public void remove(UUID id) {
        cache.remove(id);
    }

    /**
     * Очищає весь кеш.
     */
    public void clear() {
        cache.clear();
    }

    /**
     * Перевіряє наявність сутності в кеші.
     *
     * @param id ідентифікатор
     * @return true, якщо сутність є в кеші
     */
    public boolean contains(UUID id) {
        return cache.containsKey(id);
    }

    /**
     * Повертає кількість закешованих сутностей.
     *
     * @return розмір кешу
     */
    public int size() {
        return cache.size();
    }
}
