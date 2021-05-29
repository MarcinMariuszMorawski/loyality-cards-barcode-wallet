package pl.lodz.uni.math.loyalitycardsbarcodewalletapp.api.model;

import java.io.Serializable;

public final class Brand implements Serializable {
    private Long id;
    private String name;
    private String color;

    public Brand() {
    }

    public Brand(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Brand{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}