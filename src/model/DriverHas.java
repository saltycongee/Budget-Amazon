package model;

public class DriverHas {
    private final int workerID;
    private final String licencePlate;

    public DriverHas(int workerID, String licencePlate) {
        this.workerID = workerID;
        this.licencePlate = licencePlate;
    }

    public int getWorkerID() {
        return workerID;
    }

    public String getLicencePlate() {
        return licencePlate;
    }
}
