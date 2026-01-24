package fedelesh.flowersalon.infrastructure.storage.exception;

/**
 * Виняток при спробі зберегти сутність з дублікатом унікального поля.
 */
public class DuplicateEntityException extends RepositoryException {

    private final String field;
    private final Object value;

    public DuplicateEntityException(String field, Object value) {
        super(String.format("Сутність з %s='%s' вже існує", field, value));
        this.field = field;
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public Object getValue() {
        return value;
    }
}
