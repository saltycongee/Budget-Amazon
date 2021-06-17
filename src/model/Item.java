package model;

public class Item {
    private final String item_Name;
    private final int itemID;
    private final int count;
    private final int weight;
    private final double price;
    private final int sellerID;
    private final int warehouseID;

    public Item(String item_name, int itemID, int count, int weight, double price, int sellerID, int warehouseID) {
        this.item_Name = item_name;
        this.itemID = itemID;
        this.count = count;
        this.weight = weight;
        this.price = price;
        this.sellerID = sellerID;
        this.warehouseID = warehouseID;
    }

    public int getItemID() {
        return itemID;
    }

    public int getCount() {
        return count;
    }

    public int getWeight() {
        return weight;
    }

    public Double getPrice() {
        return price;
    }

    public int getSellerID() {
        return sellerID;
    }

    public int getWarehouseID() {
        return warehouseID;
    }

    public String getItem_Name() {
        return item_Name;
    }
}
