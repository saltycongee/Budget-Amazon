package model;

public class Worker {
    private final int workerID;
    private final int manager_ID;
    private final String name;
    private final String phoneNumber;
    private final int wage;

    public int getWorkerID() {
        return workerID;
    }

    public int getManager_ID() {
        return manager_ID;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getWage() {
        return wage;
    }

    public Worker(int workerID, int manager_id, String name, String phoneNumber, int wage) {
        this.workerID = workerID;
        manager_ID = manager_id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.wage = wage;
    }
}
