package nl.garagemeijer.salesapi.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Motors")
public class Motor extends Vehicle {

    @Column(nullable = false)
    private String typeMotorcycle;
    @Column(nullable = false)
    private int wheelbase;
    @Column(nullable = false)
    private String handlebarType;

    public String getTypeMotorcycle() {
        return typeMotorcycle;
    }

    public void setTypeMotorcycle(String typeMotorcycle) {
        this.typeMotorcycle = typeMotorcycle;
    }

    public int getWheelbase() {
        return wheelbase;
    }

    public void setWheelbase(int wheelbase) {
        this.wheelbase = wheelbase;
    }

    public String getHandlebarType() {
        return handlebarType;
    }

    public void setHandlebarType(String handlebarType) {
        this.handlebarType = handlebarType;
    }
}
