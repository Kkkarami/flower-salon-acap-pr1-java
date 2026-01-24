package fedelesh.flowersalon.infrastructure.storage.exception;

/**
 * Виняток, коли сутність не знайдено.
 */
public class EntityNotFoundException extends RepositoryException {

    private final Object id;

    public EntityNotFoundException(Object id) {
        super("Сутність з id='" + id + "' не знайдена");
        this.id = id;
    }

    public Object getId() {
        return id;
    }
}
