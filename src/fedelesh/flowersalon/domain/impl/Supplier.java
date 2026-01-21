package fedelesh.flowersalon.domain.impl;

import fedelesh.flowersalon.domain.BaseEntity;
import fedelesh.flowersalon.domain.exception.EntityValidationException;
import fedelesh.flowersalon.domain.util.ValidationError;

public class Supplier extends BaseEntity {

    public static final String FIELD_NAME = "companyName";
    public static final String FIELD_CONTACT = "contactPerson";
    public static final String FIELD_PHONE = "phoneNumber";

    private String companyName;
    private String contactPerson;
    private String phoneNumber;

    private Supplier() {
        super();
    }

    public Supplier(String companyName, String contactPerson, String phoneNumber) {
        this();
        setCompanyName(companyName);
        setContactPerson(contactPerson);
        setPhoneNumber(phoneNumber);

        if (!isValid()) {
            throw new EntityValidationException(getErrors());
        }
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        clearError(FIELD_NAME);
        if (companyName == null || companyName.trim().isEmpty()) {
            addError(FIELD_NAME, ValidationError.SUPPLIER_NAME_EMPTY.message());
        }
        this.companyName = companyName;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        clearError(FIELD_CONTACT);
        if (contactPerson == null || contactPerson.trim().isEmpty()) {
            addError(FIELD_CONTACT, ValidationError.CONTACT_PERSON_EMPTY.message());
        } else if (contactPerson.length() < 2) {
            addError(FIELD_CONTACT, ValidationError.CONTACT_PERSON_TOO_SHORT.message());
        }
        this.contactPerson = contactPerson;
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

    @Override
    public String toString() {
        return String.format("Постачальник: %s (Контакт: %s, Тел: %s)",
              companyName, contactPerson, phoneNumber);
    }
}