package fedelesh.flowersalon.domain.dto;

import fedelesh.flowersalon.domain.BaseEntity;

public final class LoginDto extends BaseEntity {

    private final String phoneNumber;
    private final String password;

    public LoginDto(String phoneNumber, String password) {
        super();
        this.phoneNumber = validatedPhone(phoneNumber);
        this.password = validatedPassword(password);
        ensureNoErrors();
    }

    private String validatedPhone(String phone) {
        if (phone == null || phone.isBlank()) {
            addError("phoneNumber", "Телефон обов'язковий");
        }
        return phone;
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

    public String phoneNumber() {
        return phoneNumber;
    }

    public String password() {
        return password;
    }
}
