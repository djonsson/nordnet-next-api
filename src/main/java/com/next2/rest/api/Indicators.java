package com.next2.rest.api;

import com.next2.rest.model.IndicatorList;
import com.next2.rest.model.TTL;
import com.next2.rest.util.RequestHandler;
import com.next2.rest.util.ResponseHandler;
import org.apache.log4j.Logger;
import org.json.JSONArray;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;

public class Indicators extends Session {

    private final static Logger LOG = Logger.getLogger(Indicators.class);
    private final static String API = "indicators";

    IndicatorList indicatorList;
    TTL indicatorListTTL = new TTL(1000);

    public IndicatorList getIndicators() {
        LOG.info("Entering getIndicators");

        if (indicatorListTTL.isStale()) {
            indicatorListTTL.resetRequestTime();

            Invocation.Builder invocation = webTarget.path(API).request(responseType);

            Response response = RequestHandler.GET(invocation);
            JSONArray jsonArray = ResponseHandler.asJsonArray(response);
            this.indicatorList = new IndicatorList(jsonArray);
        }
        return this.indicatorList;
    }

    //public void getIndicator() {
        //https://api.test.nordnet.se/api-docs/index.html#!/indicators/get_indicator
    //}
}
