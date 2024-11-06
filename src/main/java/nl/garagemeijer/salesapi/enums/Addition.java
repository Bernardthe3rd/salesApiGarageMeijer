package nl.garagemeijer.salesapi.enums;

import lombok.Getter;

@Getter
public enum Addition {
    DPL("Delivery package light"),
    DPS("Delivery package standard"),
    DPP("Delivery package plus");

    private final String name;

    Addition(String name) {
        this.name = name;
    }

}
