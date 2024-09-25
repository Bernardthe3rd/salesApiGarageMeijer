package nl.garagemeijer.salesapi.enums;

public enum Addition {
    TOWBAR("Towbar"),
    NAVIGATION("Navigation"),
    DPL("Delivery package light"),
    DPS("Delivery package standard"),
    DPP("Delivery package plus"),
    INSURANCE("Insurance");

    private final String name;

    Addition(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
