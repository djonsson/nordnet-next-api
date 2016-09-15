package com.next2.rest.object;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CountryList {

    List<Country> countryList = new ArrayList<>();

    public CountryList() {

    }

    public CountryList(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject item = jsonArray.getJSONObject(i);
            Country country = new Country(item);
            add(country);
        }
    }

    public void add(Country country) {
        this.countryList.add(country);
    }

    public Country getNamedCountry(String countryName) {
        for (Country country : this.countryList) {
            if (country.getCountryName().contentEquals(countryName)) {
                return country;
            }
        }
        return new Country();
    }

    public int getNumberOfCountries() {
        return this.countryList.size();
    }

    public String getCountryCodesAsCommaSeparatedString() {
        String commaSeparatedString = "";
        for (Country country : this.countryList) {
            commaSeparatedString +=  country.getCountryCode() + ",";
        }
        commaSeparatedString = commaSeparatedString.substring(0, commaSeparatedString.length()-1);
        return commaSeparatedString;
    }
}
