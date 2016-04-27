package integration;

import com.next2.rest.api.Session;
import org.json.JSONObject;
import org.junit.Test;
import com.next2.rest.util.SocketFeedHandler;

import javax.net.ssl.SSLSocket;
import java.io.*;
import java.net.SocketException;

public class TestSocketFeedHandler extends Session {

    @Test(expected=SocketException.class)
    public void socketFeedHandlerShouldReturnHeartBeat() throws IOException {

        SocketFeedHandler socketFeedHandler = new SocketFeedHandler();
        SSLSocket socketConnection = socketFeedHandler.connectPrivateFeed();
        assert(socketFeedHandler.isConnected(socketConnection));

        BufferedReader input = new BufferedReader(new InputStreamReader(socketConnection.getInputStream()));
        PrintWriter output = new PrintWriter(new OutputStreamWriter(socketConnection.getOutputStream()));

        String loginMessage = "{\"cmd\":\"login\", \"args\":{ \"session_key\":\"ABC123\", \"service\":\"NEXTAPI\" }}".replace("ABC123", sessionKey);
        String subscribe = "{\"cmd\":\"subscribe\", \"args\":{\"t\":\"price\", \"i\":\"1869\", \"m\":30}}";

        output.println(loginMessage + "\n");
        output.flush();
        //output.println(subscribe);
        //output.flush();

        String heartbeat;
        while ((input.readLine()) != null) {
            JSONObject json = new JSONObject(input.readLine());
            heartbeat = json.getString("type");
            System.out.println(heartbeat);
            assert(heartbeat.contentEquals("heartbeat"));
            socketConnection.close();
        }
    }
}

