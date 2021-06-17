package model;

public class TransportationMethod {
    private final String licencePlate;
    private final int shippingPrice;

    public TransportationMethod(String licencePlate, int shippingPrice) {
        this.licencePlate = licencePlate;
        this.shippingPrice = shippingPrice;
    }

    public String getLicencePlate() {
        return licencePlate;
    }

    public int getShippingPrice() {
        return shippingPrice;
    }
}
