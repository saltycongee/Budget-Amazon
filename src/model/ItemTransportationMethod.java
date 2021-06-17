package model;

public class ItemTransportationMethod {
    private final String city;
    private final int buildingNumber;
    private final String licencePlate;

    public ItemTransportationMethod(String city, int buildingNumber, String licencePlate) {
        this.city = city;
        this.buildingNumber = buildingNumber;
        this.licencePlate = licencePlate;
    }

    public String getCity() {
        return city;
    }

    public int getBuildingNumber() {
        return buildingNumber;
    }

    public String getLicencePlate() {
        return licencePlate;
    }
}
