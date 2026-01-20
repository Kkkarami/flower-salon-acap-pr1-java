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

    ROLE_EMPTY("Роль користувача не може бути порожньою"),

    PRODUCT_NAME_EMPTY("Назва товару не може бути порожньою"),
    PRODUCT_PRICE_INVALID("Ціна товару повинна бути більшою за 0"),

    ORDER_DATE_EMPTY("Дата замовлення не може бути порожньою"),
    ORDER_TOTAL_INVALID("Сума замовлення некоректна"),
    ORDER_STATUS_EMPTY("Статус замовлення не задано"),
    
    COMMENT_EMPTY("Коментар не може бути порожнім"),

    QUANTITY_INVALID("Кількість повинна бути більшою за 0"),
    PRICE_AT_ORDER_INVALID("Ціна на момент замовлення некоректна");


    private final String message;

    ValidationError(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
