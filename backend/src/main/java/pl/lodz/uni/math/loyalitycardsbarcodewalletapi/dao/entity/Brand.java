package pl.lodz.uni.math.loyalitycardsbarcodewalletapi.dao.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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

    public Brand(Long id, String name, String color) {
        this.id = id;
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
    @Size(max = 35)
    @NotBlank
    @Column(name = "name", length = 35, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @NotBlank
    @Size(max = 7)
    @Column(name = "color", length = 7, nullable = false)
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