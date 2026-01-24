package fedelesh.flowersalon.infrastructure.storage.exception;

/**
 * Виняток при проблемах з файловою системою.
 */
public class StorageException extends RepositoryException {

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
