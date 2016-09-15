package com.next2.rest.api;

import com.next2.rest.object.Login;
import com.next2.rest.util.ResponseHandler;
import com.next2.rest.util.ResourceReader;
import org.json.JSONObject;

import javax.crypto.Cipher;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public abstract class Authenticate {

    static ResourceReader resourceReader = new ResourceReader();
    static MediaType responseType = MediaType.APPLICATION_JSON_TYPE;
    static Entity entityType = Entity.entity("", MediaType.APPLICATION_JSON_TYPE);

    /**
     * Before any other of the services (except for the system info request) can be called the user must login.
     * The username, password and phrase must be sent encrypted.
     * <p>
     * Three steps are required:
     * 1. First Base64-encode the username, password and timestamp (epoch in milliseconds) and combine them with the character ‘:’
     * 2. Use the public key for the application and encrypt the string
     * 3. Base64 encode the encrypted string.
     *
     * @see <a href="https://api.test.nordnet.se/api-docs/index.html?#!/login/login">Login</a>
     * @return Login
     */
    public static Login login(WebTarget webTarget, String username, String password, String pemFile) {
        Response response = webTarget.path("login").queryParam("service", "NEXTAPI")
                .queryParam("auth", encryptAuthorizationParameter(username, password, pemFile)).request(responseType).post(entityType);
        JSONObject json = ResponseHandler.asJsonObject(response);
        return new Login(json);
    }

    /**
     * If the application needs to keep the session alive the session can be touched.
     * Note the basic auth header field must be set as for all other calls.
     * All calls to any REST service is touching the session.
     * So touching the session manually is only needed if no other calls are done during the session timeout interval.
     *
     * @return JSONObject
     */
    public static JSONObject loginUpdateSession(WebTarget webTarget, Login login) {
        Response response = webTarget.path("login").path(login.getSessionKey()).request(responseType).put(entityType);
        return ResponseHandler.asJsonObject(response);
    }

    /**
     * Invalidates the session
     *
     * @return JSONObject
     */
    public static JSONObject logout(WebTarget webTarget, Login login) {
        Response response = webTarget.path("login").path(login.getSessionKey()).request(responseType).delete();
        return ResponseHandler.asJsonObject(response);
    }

    private static PublicKey readPublicKeyFromDisk(String filename) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String publicKey = resourceReader.readFromResources(filename);
        publicKey = concatenatePublicKey(publicKey);

        byte[] binary = DatatypeConverter.parseBase64Binary(publicKey);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(binary);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(spec);
    }

    private static String concatenatePublicKey(String publicKey) {
        return publicKey.replaceAll("(-+BEGIN PUBLIC KEY-+\\r?\\n|-+END PUBLIC KEY-+\\r?\\n?)", "");
    }

    private static String encryptAuthorizationParameter(String username, String password, String pemFile) {
        String loginData = constructLoginData(username, password);
        byte[] encryptedBytes = new byte[0];
        try {
            PublicKey publicKey = readPublicKeyFromDisk(pemFile);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            encryptedBytes = cipher.doFinal(loginData.getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String authParam = DatatypeConverter.printBase64Binary(encryptedBytes);
        String auth = "";
        try {
            auth = URLEncoder.encode(authParam, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return auth;
    }

    private static String constructLoginData(String user, String password) {
        return DatatypeConverter.printBase64Binary(user.getBytes()) + ":" +
                DatatypeConverter.printBase64Binary(password.getBytes()) + ":" +
                DatatypeConverter.printBase64Binary(String.valueOf(System.currentTimeMillis()).getBytes());
    }
}
