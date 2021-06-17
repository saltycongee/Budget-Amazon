package model;

public class SellerAccount {
    private final int sellerID;
    private final String email;
    private final String password;
    private final String creditCardInfo;
    private final long phoneNumber;

    public SellerAccount(int sellerID, String email, String password, String creditCardInfo, long phoneNumber) {
        this.sellerID = sellerID;
        this.email = email;
        this.password = password;
        this.creditCardInfo = creditCardInfo;
        this.phoneNumber = phoneNumber;
    }

    public int getSellerID() {
        return sellerID;
    }

    public String getEmail() {
        return email;
    }

    public String getCreditCardInfo() {
        return creditCardInfo;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }
}
