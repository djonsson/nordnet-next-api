package com.next2.rest.model;

public class Amount {
    float monetaryValue;
    String currency;

    public Amount() {

    }

    public Amount(float monetaryValue, String currency) {
        this.monetaryValue = monetaryValue;
        this.currency = currency;
    }

    public float getSum() {
        return this.monetaryValue;
    }

    public String getCurrency() {
        return this.currency;
    }

}
