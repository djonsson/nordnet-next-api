package com.next2.rest.api;

import com.next2.rest.model.SystemStatusInfo;
import com.next2.rest.model.TTL;
import com.next2.rest.util.RequestHandler;
import com.next2.rest.util.ResponseHandler;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;

/**
 * General information about the System (the next2 backend) can be obtained by this class. This is the only
 * API call which does not require the user to be authenticated.
 *
 */
public class SystemQueries extends Session {
    private final static Logger LOG = Logger.getLogger(SystemQueries.class);

    SystemStatusInfo systemStatusInfo;
    TTL systemStatusInfoTTL = new TTL(1000);


    /**
     * Returns system information for the next2 backend. The response is returned from the API as a JSONObject
     * @return
     */
    public SystemStatusInfo getSystemStatusInfo() {
        LOG.info("Entering getSystemStatusInfo");

        if (systemStatusInfoTTL.isStale()) {
            systemStatusInfoTTL.resetRequestTime();

            Invocation.Builder invocation = webTarget.request(responseType);
            Response response = RequestHandler.GET(invocation);
            JSONObject jsonObject = ResponseHandler.asJsonObject(response);

            this.systemStatusInfo = new SystemStatusInfo(jsonObject);
        }
        return systemStatusInfo;
    }
}
