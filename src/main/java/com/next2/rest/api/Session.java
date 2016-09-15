package com.next2.rest.api;

import com.next2.rest.object.Login;
import org.apache.log4j.Logger;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import com.next2.rest.util.ResourceReader;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.*;

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

        List<String> requiredProperties = new ArrayList<>(
                Arrays.asList("baseurl", "username", "password", "pemfile"));

        this.throwErrorOnRequiredProperty(properties, requiredProperties);

        String baseUrl  = properties.getProperty(requiredProperties.get(0));
        String username = properties.getProperty(requiredProperties.get(1));
        String password = properties.getProperty(requiredProperties.get(2));
        String pemFile  = properties.getProperty(requiredProperties.get(3));

        webTarget = ClientBuilder.newClient().target(baseUrl);

        if (sessionKey == null) {
            log.info("Session key was not set. Authenticate the client");
            login = Authenticate.login(webTarget, username, password, pemFile);
            sessionKey = login.getSessionKey();
        }
        webTarget = webTarget.register(HttpAuthenticationFeature.basic(sessionKey, sessionKey));
    }

    private void throwErrorOnRequiredProperty(Properties properties, List<String> requiredProperties) {
        for (String property : requiredProperties) {
            String p = properties.getProperty(property);
            if (p == null) {
                throw new IllegalArgumentException("The property '" + property + "' was not found in the properties file");
            }
            //if (p.isEmpty()) {
            //    throw new IllegalArgumentException("The property '" + property + "' was empty in the properties file");
            //}
            log.info(property + ": " + p);
        }
    }
}
