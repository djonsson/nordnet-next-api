package com.next2.rest.util;
import org.apache.log4j.Logger;
import org.json.*;

import javax.ws.rs.core.Response;
public class ResponseHandler {

    final static Logger log = Logger.getLogger(ResponseHandler.class);
    public final static boolean recordResponse = true;

    public static JSONObject asJsonObject(Response response) {
        String responseAsString = responseAsString(response);
        JSONObject jsonObject = new JSONObject(responseAsString);
        if (recordResponse) {
            ResourceReader.recordForUnitTests(jsonObject.toString(4));
        }
        return jsonObject;
    }

    public static JSONArray asJsonArray(Response response) {
        String responseAsString = responseAsString(response);
        JSONArray jsonArray = new JSONArray(responseAsString);
        if (recordResponse) {
            ResourceReader.recordForUnitTests(jsonArray.toString(4));
        }
        return jsonArray;
    }

    private static String responseAsString(Response response) {
        String responseAsString = response.readEntity(String.class);
        log(response, responseAsString);
        return responseAsString;
    }

    private static void log(Response response, String responseAsString) {
        log.debug(response);
        log.debug(responseAsString);
        log.debug(response.getCookies());
        log.debug(response.getHeaders());
        log.debug(response.getLocation());
    }

    /**
     *
     *       Response response = null;
     while (response == null || response.getStatus() == 404) {
     Invocation.Builder req = webTarget.path("accounts1").request(responseType);
     response = req.get();
     System.out.println(response.getStatusInfo());
     try {
     Thread.sleep(5000);
     } catch (InterruptedException e) {
     e.printStackTrace();
     }
     }
     */
}
