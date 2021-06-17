package model;

public class OrderList {
    private final int orderListID;
    private final int customerID;

    public OrderList(int orderListID, int customerID) {
        this.orderListID = orderListID;
        this.customerID = customerID;
    }

    public int getOrderListID() {
        return orderListID;
    }

    public int getCustomerID() {
        return customerID;
    }
}
