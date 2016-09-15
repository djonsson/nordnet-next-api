package integration;

import com.next2.rest.api.Session;
import com.next2.rest.object.Login;
import org.junit.Test;
import com.next2.rest.util.ResourceReader;

import java.io.IOException;
import java.util.Properties;

public class TestSession {

    ResourceReader resourceReader = new ResourceReader();

    @Test
    public void authenticationReturnsAValidSession() throws IOException {
        Properties testProperties = resourceReader.getProperties("next-test-environment.properties");
        new Session(testProperties);
        //assert(!Session.sessionKey.isEmpty());
        //assert(Session.sessionKey.length() == 40);
    }

    @Test
    public void authenticateLoginUpdateSession() {
        Session session = new Session();
        Login login = session.getLoginObject();

        //JSONObject json = auth.loginUpdateSession();
        //assert(json.getBoolean("logged_in"));
    }

    @Test
    public void authenticateLogOutLogsOutUser() {
        Session auth = new Session();
        //JSONObject json = auth.logout();
        //assert(!json.getBoolean("logged_in"));
    }
}
