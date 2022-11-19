package com.example.debtnote;

import java.io.Serializable;

public class Qpp implements Serializable{
    private String quantaty;
    private String product;
    private String price;

    public Qpp(String quantaty, String product, String price) {
        this.quantaty = quantaty;
        this.product = product;
        this.price = price;
    }

    public String getQuantaty() {
        return quantaty;
    }

    public void setQuantaty(String quantaty) {
        this.quantaty = quantaty;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
