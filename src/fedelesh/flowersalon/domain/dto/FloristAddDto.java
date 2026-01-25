package fedelesh.flowersalon.domain.dto;

import fedelesh.flowersalon.domain.BaseEntity;
import fedelesh.flowersalon.domain.enums.WorkerRole;
import java.util.UUID;
import java.util.regex.Pattern;

public final class FloristAddDto extends BaseEntity {

    private final String firstName;
    private final String lastName;
    private final String phoneNumber;
    private final String password;
    private final WorkerRole role;

    public FloristAddDto(UUID id, String firstName, String lastName,
          String phoneNumber, String password, WorkerRole role) {
        super(); // BaseEntity згенерує ID
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = validatedPhone(phoneNumber);
        this.password = validatedPassword(password);
        this.role = role;

        ensureNoErrors();
    }

    private String validatedPhone(String phone) {
        if (phone == null || phone.isBlank()) {
            addError("phoneNumber", "Номер телефону обов'язковий");
        } else if (!phone.matches("\\+380\\d{9}")) {
            addError("phoneNumber", "Формат телефону має бути +380XXXXXXXXX");
        }
        return phone;
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

    public String phoneNumber() {
        return phoneNumber;
    }

    public String password() {
        return password;
    }

    public WorkerRole role() {
        return role;
    }
}

