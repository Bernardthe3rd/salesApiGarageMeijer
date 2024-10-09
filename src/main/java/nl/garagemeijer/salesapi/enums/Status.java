package nl.garagemeijer.salesapi.enums;

public enum Status {
    NEW("New"),
    OPEN("Open"),
    CLOSED("Closed"),
    PENDING("Pending"),
    ;

    private final String name;

    Status(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
