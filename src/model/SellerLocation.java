package model;

public class SellerLocation {
    private final int sellerID;
    private final String postalCode;
    private final int buildingNumber;

    public SellerLocation(int sellerID, String postalCode, int buildingNumber) {
        this.sellerID = sellerID;
        this.postalCode = postalCode;
        this.buildingNumber = buildingNumber;
    }

    public int getSellerID() {
        return sellerID;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public int getBuildingNumber() {
        return buildingNumber;
    }
}
