package com.next2.rest.api;

import com.next2.rest.model.Login;
import org.apache.log4j.Logger;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import com.next2.rest.util.ResourceReader;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.Properties;

public class Session {
    public Properties properties;

    public static String sessionKey;
    private static Login login;

    static WebTarget webTarget;
    static MediaType responseType = MediaType.APPLICATION_JSON_TYPE;

    final static Logger log = Logger.getLogger(Session.class);
    final static ResourceReader resourceReader = new ResourceReader();

    public Login getLoginObject() {
        log.info("Returning the login response: " + login.toString());
        return login;
    }

    public Session() {
        String defaultPropertiesFile = "next-test-environment.properties";
        log.info("No properties file specified, using default: " + defaultPropertiesFile);

        properties = resourceReader.getProperties("next-test-environment.properties");
        new Session(properties);
    }

    public Session(Properties properties) {
        log.info("Initializing a new session with a specified properties file" );
        log.info(properties.toString());

        String baseUrl  = properties.getProperty("baseurl");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        String pemFile  = properties.getProperty("pemfile");

        webTarget = ClientBuilder.newClient().target(baseUrl);

        if (sessionKey == null) {
            log.info("Session key was not set. Authenticate the client");
            login = Authenticate.login(webTarget, username, password, pemFile);
            sessionKey = login.getSessionKey();
        }
        webTarget = webTarget.register(HttpAuthenticationFeature.basic(sessionKey, sessionKey));
    }
}
