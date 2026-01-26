package fedelesh.flowersalon.domain.dto;

import fedelesh.flowersalon.domain.BaseEntity;

public final class LoginDto extends BaseEntity {

    private final String email;
    private final String password;

    public LoginDto(String email, String password) {
        super();
        this.email = validatedPhone(email);
        this.password = validatedPassword(password);
        ensureNoErrors();
    }

    private String validatedPhone(String email) {
        if (email == null || email.isBlank()) {
            addError("phoneNumber", "Телефон обов'язковий");
        }
        return email;
    }

    private String validatedPassword(String pass) {
        if (pass == null || pass.isBlank()) {
            addError("password", "Пароль обов'язковий");
        }
        return pass;
    }

    private void ensureNoErrors() {
        if (!isValid()) {
            throw new IllegalArgumentException("Помилка входу: " + getErrors());
        }
    }

    public String email() {
        return email;
    }

    public String password() {
        return password;
    }
}
