package model;

public class LocationTransportationMethod {
    private final String city;
    private final String street;
    private final int buildingNumber;
    private final String licencePlate;

    public LocationTransportationMethod(String city, String street, int buildingNumber, String licencePlate) {
        this.city = city;
        this.street = street;
        this.buildingNumber = buildingNumber;
        this.licencePlate = licencePlate;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public int getBuildingNumber() {
        return buildingNumber;
    }

    public String getLicencePlate() {
        return licencePlate;
    }
}
