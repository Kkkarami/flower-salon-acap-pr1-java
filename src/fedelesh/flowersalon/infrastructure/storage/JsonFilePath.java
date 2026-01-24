package fedelesh.flowersalon.infrastructure.storage;

public enum JsonFilePath {
    ACCESSORIES("data/accessories.json"),
    BOUQUETS("data/bouquets.json"),
    FLORISTS("data/florists.json"),
    FLOWERS("data/flowers.json"),
    ORDERS("data/orders.json"),
    SUPPLIERS("data/suppliers.json"),
    BOUQUET_FLOWER("data/bouquet_flower.json"),
    ORDER_ITEM("data/order_item.json");


    private final String path;

    JsonFilePath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
