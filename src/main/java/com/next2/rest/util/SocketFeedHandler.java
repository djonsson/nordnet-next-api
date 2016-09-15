package com.next2.rest.util;

import com.next2.rest.api.Session;
import com.next2.rest.object.Login;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.util.Properties;

public class SocketFeedHandler extends Session {

    public SocketFeedHandler() {
        if (sessionKey == null) {
            new Session();
        }
    }

    public SocketFeedHandler(Properties properties) {
        new Session(properties);
    }

    public SSLSocket connectPrivateFeed() {
        Login loginObject = getLoginObject();
        return connect(loginObject.getPrivateFeedHostname(), loginObject.getPrivateFeedPort());
    }

    public SSLSocket connectPublicFeed() {
        Login loginObject = getLoginObject();
        return connect(loginObject.getPublicFeedHostname(), loginObject.getPublicFeedPort());
    }

    private SSLSocket connect(String hostname, int port) {
        SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        try {
            return (SSLSocket) sslSocketFactory.createSocket(hostname, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isConnected(SSLSocket sslSocket) {
        return sslSocket.isConnected();
    }
}
