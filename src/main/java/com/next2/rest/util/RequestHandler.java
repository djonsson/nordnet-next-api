package com.next2.rest.util;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;

public class RequestHandler {

    public static Response GET(Invocation.Builder invocation) {
        Response response = invocation.get();
        if (response.getStatus() != 200) {
            System.out.println("Failed to request the source. Implement retry logic here");
            return null;
        }
        return response;
    }
}
