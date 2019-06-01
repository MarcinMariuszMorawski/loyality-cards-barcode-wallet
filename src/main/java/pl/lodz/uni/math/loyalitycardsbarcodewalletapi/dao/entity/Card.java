package pl.lodz.uni.math.loyalitycardsbarcodewalletapi.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public final class Card {
    private Long id;
    private String barcode;
    private User user;
    private Brand brand;

    public Card() {
    }

    public Card(String barcode, User user, Brand brand) {
        this.barcode = barcode;
        this.user = user;
        this.brand = brand;
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
    @NotBlank
    @NotNull
    @Size(max = 35)
    @Column(name = "barcode", length = 35, nullable = false)
    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_user")
    @JsonIgnore

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_brand")
    //@JsonIgnore

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", barcode='" + barcode + '\'' +
                ", user=" + user +
                ", brand=" + brand +
                '}';
    }
}