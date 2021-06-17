package model;

public class Warehouse {
    private final int wareHouseID;
    private final String postalCode;
    private final int buildingNumber;

    public int getWareHouseID() {
        return wareHouseID;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public int getBuildingNumber() {
        return buildingNumber;
    }

    public Warehouse(int wareHouseID, String postalCode, int buildingNumber) {
        this.wareHouseID = wareHouseID;
        this.postalCode = postalCode;
        this.buildingNumber = buildingNumber;
    }
}
