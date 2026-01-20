package fedelesh.flowersalon.domain.exception;

import java.util.List;
import java.util.Map;

public class EntityValidationException extends RuntimeException {

    private final Map<String, List<String>> errors;

    public EntityValidationException(Map<String, List<String>> errors) {
        super("Entity validation failed: ");
        this.errors = errors;
    }

    public Map<String, List<String>> getErrors() {
        return errors;
    }

    @Override
    public String getMessage() {
        StringBuilder sb = new StringBuilder(super.getMessage());
        sb.append("\n");
        errors.forEach((field, messages) -> {
            sb.append(field).append(": ").append("\n");
        });
        return sb.toString();
    }
}
