package integration;

import com.next2.rest.api.Markets;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestMarkets {

    @Test
    public void marketsShouldReturnTradableMarkets() {
        int expectedMarkets = 26;
        Markets markets = new Markets();
        JSONArray jsonArray = markets.markets();
        assert(jsonArray.length() == expectedMarkets) :
                "Expected to get " + expectedMarkets + "but saw " + jsonArray.length();
    }

    @Test
    public void marketSpecific() {
        List<Integer> marketList = Arrays.asList(80, 40);
        List<Integer> marketListResults = new ArrayList<>();
        Markets markets = new Markets();
        JSONArray jsonArray = markets.markets(marketList);
        for (int i=0; i< jsonArray.length(); i++) {
            JSONObject item = jsonArray.getJSONObject(i);
            marketListResults.add(item.getInt("market_id"));
        }
        assert(marketListResults.containsAll(marketList)) :
                "Expected marketListResults to contain all marketList items";
    }
}
