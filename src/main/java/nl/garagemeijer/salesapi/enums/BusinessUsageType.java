package nl.garagemeijer.salesapi.enums;

import lombok.Getter;

@Getter
public enum BusinessUsageType {
    GOVERNMENT("Government"),
    GARDENER("Gardener"),
    BUILDER("Builder"),
    PAINTER("Painter"),
    CONSULTING("Consulting"),
    SALES("Sales"),
    EMERGENCYSERVICES("Emergency service"),
    IT("ICT"),
    FINANCE("Finance"),
    HRM("Human resources"),
    OTHER("Other");

    private final String name;

    BusinessUsageType(String name) {
        this.name = name;
    }

}
