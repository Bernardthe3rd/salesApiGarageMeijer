package nl.garagemeijer.salesapi.enums;

import lombok.Getter;

@Getter
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

}
