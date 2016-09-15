package com.next2.rest.object;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.json.JSONObject;

public class Login {
        String sessionKey;
        String environment;
        String privateFeedHostname;
        String publicFeedHostname;
        Integer privateFeedPort;
        Integer publicFeedPort;

    public Login(JSONObject json) {
        this.sessionKey          = json.getString("session_key");
        this.environment         = json.getString("environment");
        this.privateFeedHostname = json.getJSONObject("private_feed").getString("hostname");
        this.publicFeedHostname  = json.getJSONObject("public_feed").getString("hostname");
        this.privateFeedPort     = json.getJSONObject("private_feed").getInt("port");
        this.publicFeedPort      = json.getJSONObject("public_feed").getInt("port");
    }

    public String getSessionKey() {
        return this.sessionKey;
    }

    public String getEnvironment() {
        return this.environment;
    }

    public String getPrivateFeedHostname() {
        return this.privateFeedHostname;
    }

    public String getPublicFeedHostname() {
        return this.publicFeedHostname;
    }

    public Integer getPrivateFeedPort() {
        return this.privateFeedPort;
    }

    public Integer getPublicFeedPort() {
        return this.publicFeedPort;
    }

    public String toString() {
        return new ToStringBuilder(this).
                append("sessionKey", sessionKey).
                append("environment", environment).
                append("privateFeedHostname", privateFeedHostname).
                append("publicFeedHostname", publicFeedHostname).
                append("privateFeedPort", privateFeedPort).
                append("publicFeedPort", publicFeedPort).
                toString();
    }

}
