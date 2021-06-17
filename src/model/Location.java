package model;

public class Location {
    public final String postalCode;
    public final int buildingNumber;

    public Location(String postalCode, int buildingNumber) {
        this.postalCode = postalCode;
        this.buildingNumber = buildingNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public int getBuildingNumber() {
        return buildingNumber;
    }
}
