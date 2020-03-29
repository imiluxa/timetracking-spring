package ua.iamiluxa.timetrackingspringproject.entity;


public enum RequestActions {
    ADD("Add"),
    END("End");

    private final String action;

    RequestActions(String action) { this.action = action; }

    @Override
    public String toString() { return action; }
}
