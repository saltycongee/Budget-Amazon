package model;

public class PostalCodeLocation {
    private final String postalCode;
    private final String city;
    private final String street;

    public PostalCodeLocation(String postalCode, String city, String street) {
        this.postalCode = postalCode;
        this.city = city;
        this.street = street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }
}
