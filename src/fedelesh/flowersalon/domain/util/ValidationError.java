package fedelesh.flowersalon.domain.util;

public enum ValidationError {
    FIRST_NAME_EMPTY("Імʼя не може бути порожнім"),
    FIRST_NAME_TOO_SHORT("Імʼя повинно містити щонайменше 2 символи"),

    LAST_NAME_EMPTY("Прізвище не може бути порожнім"),
    LAST_NAME_TOO_SHORT("Прізвище повинно містити щонайменше 2 символи"),

    PASSWORD_EMPTY("Пароль не може бути порожнім"),
    PASSWORD_TOO_SHORT("Пароль повинен містити щонайменше 6 символів"),

    PHONE_EMPTY("Номер телефону не може бути порожнім"),
    PHONE_INVALID("Невірний формат номера телефону"),

    EMAIL_INVALID("Некоректний формат електронної пошти."),

    ROLE_EMPTY("Роль користувача не може бути порожньою"),

    PRODUCT_QUANTITY_MINUS("Кількість не може бути від’ємною"),
    PRODUCT_NAME_EMPTY("Назва товару не може бути порожньою"),
    PRODUCT_PRICE_INVALID("Ціна товару повинна бути більшою за 0"),

    ORDER_DATE_EMPTY("Дата замовлення не може бути порожньою"),
    ORDER_TOTAL_INVALID("Сума замовлення некоректна"),
    ORDER_STATUS_EMPTY("Статус замовлення не задано"),

    QUANTITY_INVALID("Кількість повинна бути більшою за 0"),
    PRICE_AT_ORDER_INVALID("Ціна на момент замовлення некоректна"),

    BOUQUET_NAME_EMPTY("Назва букета не може бути порожньою"),
    BOUQUET_NAME_TOO_SHORT("Назва букета повинна містити щонайменше 2 символи"),
    BOUQUET_PRICE_INVALID("Ціна букета повинна бути більшою за 0"),
    BOUQUET_DESCRIPTION_EMPTY("Опис букета не може бути порожнім"),

    ACCESSORY_NAME_EMPTY("Назва аксесуара не може бути порожньою"),
    ACCESSORY_NAME_TOO_SHORT("Назва аксесуара повинна містити щонайменше 2 символи"),
    ACCESSORY_PRICE_INVALID("Ціна аксесуара повинна бути більшою за 0"),
    ACCESSORY_DESCRIPTION_EMPTY("Опис аксесуара не може бути порожнім"),

    BOUQUET_FLOWER_QUANTITY_INVALID("Кількість квітів повинна бути більшою за 0"),
    BOUQUET_FLOWER_PRICE_INVALID("Ціна квітки некоректна"),

    SUPPLIER_NAME_EMPTY("Назва компанії постачальника не може бути порожньою"),
    CONTACT_PERSON_EMPTY("Контактна особа не може бути порожньою"),
    CONTACT_PERSON_TOO_SHORT("Ім'я контактної особи повинно містити щонайменше 2 символи");


    private final String message;

    ValidationError(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
