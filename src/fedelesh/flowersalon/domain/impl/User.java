package fedelesh.flowersalon.domain.impl;

import fedelesh.flowersalon.domain.BaseEntity;
import fedelesh.flowersalon.domain.enums.UserRole;
import fedelesh.flowersalon.domain.exception.EntityValidationException;
import fedelesh.flowersalon.domain.util.ValidationError;
import java.util.UUID;

public class User extends BaseEntity implements Comparable<User> {

    public static final String FIELD_FIRST_NAME = "firstName";
    public static final String FIELD_LAST_NAME = "lastName";
    public static final String FIELD_PASSWORD = "hashPassword";
    public static final String FIELD_PHONE = "phoneNumber";
    public static final String FIELD_ROLE = "role";

    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String hashPassword;
    private UserRole role;

    private User() {
        super();
    }

    public User(UUID id, String firstName, String lastName, String hashPassword,
          String phoneNumber, UserRole role) {
        this();
        setFirstName(firstName);
        setLastName(lastName);
        setHashPassword(hashPassword);
        setPhoneNumber(phoneNumber);
        setRole(role);

        if (!isValid()) {
            throw new EntityValidationException(getErrors());
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        clearError(FIELD_FIRST_NAME);
        if (firstName == null || firstName.trim().isEmpty()) {
            addError(FIELD_FIRST_NAME, ValidationError.FIRST_NAME_EMPTY.message());
        } else if (firstName.length() < 2) {
            addError(FIELD_FIRST_NAME, ValidationError.FIRST_NAME_TOO_SHORT.message());
        }
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        clearError(FIELD_LAST_NAME);
        if (lastName == null || lastName.trim().isEmpty()) {
            addError(FIELD_LAST_NAME, ValidationError.LAST_NAME_EMPTY.message());
        } else if (lastName.length() < 2) {
            addError(FIELD_LAST_NAME, ValidationError.LAST_NAME_TOO_SHORT.message());
        }
        this.lastName = lastName;
    }

    public String getHashPassword() {
        return hashPassword;
    }

    public void setHashPassword(String hashPassword) {
        clearError(FIELD_PASSWORD);
        if (hashPassword == null || hashPassword.trim().isEmpty()) {
            addError(FIELD_PASSWORD, ValidationError.PASSWORD_EMPTY.message());
        } else if (hashPassword.length() < 6) {
            addError(FIELD_PASSWORD, ValidationError.PASSWORD_TOO_SHORT.message());
        }
        this.hashPassword = hashPassword;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        clearError(FIELD_PHONE);
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            addError(FIELD_PHONE, ValidationError.PHONE_EMPTY.message());
        } else if (!phoneNumber.matches("\\+?[0-9]{7,15}")) {
            addError(FIELD_PHONE, ValidationError.PHONE_INVALID.message());
        }
        this.phoneNumber = phoneNumber;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        clearError(FIELD_ROLE);

        if (role == null) {
            addError(FIELD_ROLE, ValidationError.ROLE_EMPTY.message());
        }

        this.role = role;
    }

    @Override
    public int compareTo(User o) {
        if (this.lastName == null) {
            return -1;
        }
        return this.lastName.compareTo(o.lastName);
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", firstName, lastName, phoneNumber);
    }
}
