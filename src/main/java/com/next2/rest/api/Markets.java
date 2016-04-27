package com.next2.rest.api;

import jersey.repackaged.com.google.common.base.Joiner;
import org.json.JSONArray;
import com.next2.rest.util.ResponseHandler;

import javax.ws.rs.core.Response;
import java.util.List;

@Deprecated
public class Markets extends Session {

    /**
     * Get all tradable markets. Market 80 is the smart order market.
     * Instruments that can be traded on 2 or more markets gets a tradable on the smart order market.
     * Orders entered with the smart order tradable get smart order routed with the current Nordnet best execution policy.
     *
     * @return JSONObject
     */
    public JSONArray markets() {
        Response response = webTarget.path("markets").request(responseType).get();
        return ResponseHandler.asJsonArray(response);
    }

    public JSONArray markets(int marketId) {
        Response response = webTarget.path("markets").path(String.valueOf(marketId)).request(responseType).get();
        return ResponseHandler.asJsonArray(response);
    }

    public JSONArray markets(List<Integer> marketIds) {
        Response response = webTarget.path("markets").path(Joiner.on(",").join(marketIds)).request(responseType).get();
        return ResponseHandler.asJsonArray(response);
    }
}
