package fedelesh.flowersalon.domain.impl;

import fedelesh.flowersalon.domain.BaseEntity;
import fedelesh.flowersalon.domain.enums.WorkerRole;
import fedelesh.flowersalon.domain.exception.EntityValidationException;
import fedelesh.flowersalon.domain.util.ValidationError;

public class Florist extends BaseEntity implements Comparable<Florist> {

    public static final String FIELD_FIRST_NAME = "firstName";
    public static final String FIELD_LAST_NAME = "lastName";
    public static final String FIELD_PASSWORD = "hashPassword";
    public static final String FIELD_EMAIL = "email";
    public static final String FIELD_ROLE = "role";

    private String email;
    private String firstName;
    private String lastName;
    private String passwordHash;
    private WorkerRole role;

    private Florist() {
        super();
    }

    public Florist(String firstName, String lastName, String passwordHash,
          String email, WorkerRole role) {
        this();
        setFirstName(firstName);
        setLastName(lastName);
        setPasswordHash(passwordHash);
        setEmail(email);
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

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        clearError(FIELD_PASSWORD);
        if (passwordHash == null || passwordHash.trim().isEmpty()) {
            addError(FIELD_PASSWORD, ValidationError.PASSWORD_EMPTY.message());
        } else if (passwordHash.length() < 6) {
            addError(FIELD_PASSWORD, ValidationError.PASSWORD_TOO_SHORT.message());
        }
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        clearError(FIELD_EMAIL);
        // Регулярний вираз для перевірки формату email
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            addError(FIELD_EMAIL, ValidationError.EMAIL_INVALID.message());
        }
        this.email = email;
    }

    public WorkerRole getRole() {
        return role;
    }

    public void setRole(WorkerRole role) {
        clearError(FIELD_ROLE);

        if (role == null) {
            addError(FIELD_ROLE, ValidationError.ROLE_EMPTY.message());
        }

        this.role = role;
    }

    @Override
    public int compareTo(Florist o) {
        if (this.lastName == null) {
            return -1;
        }
        return this.lastName.compareTo(o.lastName);
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", firstName, lastName, email);
    }
}
