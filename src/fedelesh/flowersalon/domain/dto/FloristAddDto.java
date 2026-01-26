package fedelesh.flowersalon.domain.dto;

import fedelesh.flowersalon.domain.BaseEntity;
import fedelesh.flowersalon.domain.enums.WorkerRole;
import java.util.regex.Pattern;

public final class FloristAddDto extends BaseEntity {

    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
    private final WorkerRole role;

    public FloristAddDto(String firstName, String lastName,
          String email, String password, WorkerRole role) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = validatedEmail(email);
        this.password = validatedPassword(password);
        this.role = role;

        ensureNoErrors();
    }

    private String validatedEmail(String email) {
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            addError("email", "Невірний формат email");
        }
        return email;
    }

    private String validatedPassword(String rawPassword) {
        if (rawPassword == null || rawPassword.isBlank()) {
            addError("password", "Пароль обов'язковий");
            return rawPassword;
        }
        if (rawPassword.length() < 6) {
            addError("password", "Пароль має бути не менше 6 символів");
        }
        var pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$");
        if (!pattern.matcher(rawPassword).matches()) {
            addError("password", "Пароль має містити велику літеру, малу та цифру");
        }
        return rawPassword;
    }

    private void ensureNoErrors() {
        if (!this.isValid()) {
            throw new IllegalArgumentException("Помилка валідації DTO: " + getErrors());
        }
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }

    public String email() {
        return email;
    }

    public String password() {
        return password;
    }

    public WorkerRole role() {
        return role;
    }
}

