package com.example.debtnote;

public class DP {
    private String day;
    private String price;

    public DP(String day, String price) {
        this.day = day;
        this.price = price;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
