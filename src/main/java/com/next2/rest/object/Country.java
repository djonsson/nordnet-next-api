package com.next2.rest.object;

import org.json.JSONObject;


public class Country {
    String countryCode;
    String countryName;

    public Country() {

    }

    public Country(JSONObject jsonObject) {
        this.countryCode = jsonObject.getString("country").toUpperCase();
        this.countryName = jsonObject.getString("name");
    }

    public Country(String countryCode, String name) {
        this.countryCode = countryCode;
        this.countryName = name;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public String getCountryName() {
        return this.countryName;
    }
}
