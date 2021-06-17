package model;

public class Customer {


    private final String email;
    private final String password;
    private final long phoneNumber;
    private final String creditCardInfo;
    private final int customerID;
    private final int orderListID;
    private final String postalCode;
    private final int buildingNumber;


    public Customer(String email, String password, long phoneNumber, String creditCardInfo, int customerID, int orderListID, String postalCode, int buildingNumber) {
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.creditCardInfo = creditCardInfo;
        this.customerID = customerID;
        this.orderListID = orderListID;
        this.postalCode = postalCode;
        this.buildingNumber = buildingNumber;
    }

    public String getEmail() {
        return email;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public String getCreditCardInfo() {
        return creditCardInfo;
    }

    public int getCustomerID() {
        return customerID;
    }

    public int getOrderListID() {
        return orderListID;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public int getBuildingNumber() {
        return buildingNumber;
    }

    public String getPassword() {
        return password;
    }
}
