package model;

public class ItemsOrdered {
    private final int itemID;
    private final int orderListID;

    public ItemsOrdered(int itemID, int orderListID) {
        this.itemID = itemID;
        this.orderListID = orderListID;
    }

    public int getItemID() {
        return itemID;
    }

    public int getOrderListID() {
        return orderListID;
    }
}
