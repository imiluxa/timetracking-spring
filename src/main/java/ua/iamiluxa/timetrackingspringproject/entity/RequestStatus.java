package ua.iamiluxa.timetrackingspringproject.entity;

public enum RequestStatus {
    CONFIRMED("confirmed"),
    DECLINED("Declined"),
    WAITING("Waiting");

    private final String status;

    RequestStatus(String status) { this.status = status; }

    @Override
    public String toString() { return status; }
}
