package model;

public class ManagesWarehouse {
    private final int workerID;
    private final int warehouseID;

    public int getWorkerID() {
        return workerID;
    }

    public int getWarehouseID() {
        return warehouseID;
    }

    public ManagesWarehouse(int workerID, int warehouseID) {
        this.workerID = workerID;
        this.warehouseID = warehouseID;
    }
}
