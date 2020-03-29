package ua.iamiluxa.timetrackingspringproject.entity;

public enum ActivityStatus {
    WAITING("Waiting"),
    INPROGRESS("Inprogress"),
    COMPLETED("Completed");

    private final String status;

    ActivityStatus(String status) { this.status = status; }

    @Override
    public String toString() { return status; }
}
