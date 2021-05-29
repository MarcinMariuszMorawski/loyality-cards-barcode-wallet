package pl.lodz.uni.math.loyalitycardsbarcodewalletapp.api.model;

import java.io.Serializable;

public final class Card implements Serializable {
    private Long id;
    private String barcode;
    private String format;
    private Brand brand;

    public Card() {
    }

    public Card(String barcode, String format, Brand brand) {
        this.barcode = barcode;
        this.format = format;
        this.brand = brand;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", barcode='" + barcode + '\'' +
                ", format='" + format + '\'' +
                ", brand=" + brand +
                '}';
    }
}