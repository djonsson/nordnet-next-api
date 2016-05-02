package com.next2.rest.model;

import org.json.JSONObject;


public class Indicator {
    Integer delayed;
    String src;
    String identifier;
    String type;
    Country country;
    String open;
    String close;
    String name;

    public Indicator(JSONObject jsonObject) {
        this.delayed        = jsonObject.optInt("delayed", 900);
        this.src            = jsonObject.getString("src");
        this.identifier     = jsonObject.getString("identifier");
        this.type           = jsonObject.getString("type");
        this.country        = new Country(jsonObject.optString("country", null), null);
        this.open           = jsonObject.optString("open", null);
        this.close          = jsonObject.optString("close", null);
        this.name           = jsonObject.getString("name");
    }

    public int getDelayed() {
        return this.delayed;
    }

    public String src() {
        return this.src;
    }

    public String identifier() {
        return this.identifier;
    }

    public String type() {
        return this.type;
    }

    public Country country() {
        return this.country;
    }

    public String open() {
        return this.open;
    }

    public String close() {
        return this.close;
    }

    public String name() {
        return this.name;
    }

    public void print() {
        int pad = 15;
        System.out.println("-Indicator-");
        System.out.println(padRight("Name:", pad)       + this.name());
        System.out.println(padRight("Country:", pad)    + this.country().getCountryCode());
        System.out.println(padRight("Identifier:", pad) + this.identifier());
        System.out.println(padRight("Src:", pad)        + this.src());
        System.out.println(padRight("Type:", pad)       + this.type());
        System.out.println(padRight("Delayed:", pad)    + this.getDelayed());
        System.out.println(padRight("Open:", pad)       + this.open());
        System.out.println(padRight("Close:", pad)      + this.close());
        System.out.println("-");
    }

    public static String padRight(String s, int n) {
        return String.format("%1$-" + n + "s", s);
    }

}

