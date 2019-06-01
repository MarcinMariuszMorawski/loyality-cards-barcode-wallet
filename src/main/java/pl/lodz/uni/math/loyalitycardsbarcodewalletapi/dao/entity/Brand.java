package pl.lodz.uni.math.loyalitycardsbarcodewalletapi.dao.entity;

import javax.persistence.*;

@Entity
public final class Brand {
    private Long id;
    private String name;
    private String color;

    public Brand() {
    }

    public Brand(String name, String color) {
        this.name = name;
        this.color = color;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "color")
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}