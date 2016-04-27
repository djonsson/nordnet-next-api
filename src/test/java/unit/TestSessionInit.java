package unit;

import com.next2.rest.api.Session;
import com.next2.rest.model.Login;
import com.next2.rest.util.ResourceReader;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import helpers.RecordedResponses;
import org.hamcrest.CoreMatchers;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class TestSessionInit {
    ResourceReader resourceReader = new ResourceReader();
    RecordedResponses recordedResponses = new RecordedResponses();
    MockWebServer server;

    @Before
    public void setUp() throws IOException {
        server = new MockWebServer();
        server.start();
    }

    @After
    public void tearDown() throws IOException {
        server.shutdown();
    }

    @Ignore("There is a problem in this test as the sessionKey gets updated by the integration tests and failing this tests.")
    @Test
    public void sessionInitShouldReturnSessionKey() throws IOException {
        HttpUrl baseUrl = server.url("/next/2/");

        JSONObject sessionInitRecorded = recordedResponses.sessionInit();
        server.enqueue(new MockResponse().setBody(sessionInitRecorded.toString()));

        Properties properties = resourceReader.getProperties("test.properties");
        properties.setProperty("baseurl", baseUrl.toString());

        Session session = new Session(properties);
        Login loginObject = session.getLoginObject();

        assertThat("The sessionkey from login object should match the recorded session key",
                loginObject.getSessionKey(), equalTo(sessionInitRecorded.getString("session_key")));

        assertThat("Only one request was made to the mock server",
                server.getRequestCount(), equalTo(1));
    }
}
