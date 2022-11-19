package com.example.debtnote;

public class PP {
    private String ProductName;
    private String Price;

    public PP(String productName, String price) {
        ProductName = productName;
        Price = price;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}
