package org.escuelaIng.repository.propertie;
import jakarta.persistence.*;

import java.io.Serializable;


@Entity
public class Propertie implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private long propertyID;
    private String address;
    private long price;
    private long size;
    private String description;


    public long getPropertyID() {
        return propertyID;
    }

    public void setPropertyID(long propertyID) {
        this.propertyID = propertyID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
