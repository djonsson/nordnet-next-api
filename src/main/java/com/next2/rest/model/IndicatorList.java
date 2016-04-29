package com.next2.rest.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class IndicatorList {
    List<Indicator> indicatorList = new ArrayList<>();

    public IndicatorList(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject item = jsonArray.getJSONObject(i);
            Indicator indicator = new Indicator(item);
            add(indicator);
        }
    }

    private void add(Indicator indicator) {
        this.indicatorList.add(indicator);
    }

    public int getNumberOfIndicators() {
        return this.indicatorList.size();
    }

    public void print() {
        for (Indicator indicator : indicatorList) {
            indicator.print();
        }
    }

    public Indicator getIndicatorByIndex(int index) {
        return indicatorList.get(index);
    }
}
