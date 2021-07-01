package swingcomponent.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.Headers;
import swingcomponent.jsobject.User;
import swingcomponent.util.JSONUtils;

import java.awt.*;
import java.io.IOException;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;

public class API {
    private static API instance;

    private static final String BASE_URL = "http://localhost:3000/api";

    public static API getInstance() {
        if (instance == null) {
            instance = new API();
        }
        return instance;
    }

    private final CookieManager cookieManager;
    private final HttpClient client;
    //private String userAuthToken;

    private API() {
        this.cookieManager = new CookieManager();

        this.client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NEVER)
                .connectTimeout(Duration.ofSeconds(10))
                .cookieHandler(this.cookieManager)
                .build();
    }

    public List getUserCredential(String username, String password) throws IOException, InterruptedException {

        HashMap values = new HashMap<String, String>() {{
            put("username", username);
            put ("password", password);
        }};

        //System.out.println(username + "  " + password);

        String json = JSONUtils.toJSON(values);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/login"))
                .setHeader("Content-Type", "application/json")
                .setHeader("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        String responseJson = response.body();

        int statusCode = response.statusCode();
        //System.out.println(statusCode);

        //List<String> listResponse = new ArrayList<>();
        //System.out.println("Getting user Credential");

        //System.out.println("Got Response");
        //System.out.println(response);
        List userList = null;
        if (statusCode == 200) {
            userList = JSONUtils.toList(responseJson, User.class);
        } else if (statusCode == 401) {
            showMessageDialog(null, "User is not an admin");
            return null;
        } else if (statusCode == 400) {
            showMessageDialog(null, "Incorrect Login Detail");
            return null;
        }
        return userList;
    }

    public String getResultAfterDeleteUser(Integer deleteUserID) throws IOException, InterruptedException {

        String authToken = getAuthToken();

        HashMap values = new HashMap<String, Object>() {{
            put("userID", deleteUserID);
            put("authToken", authToken);
        }};

        String json = JSONUtils.toJSON(values);
        //System.out.println(json);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/users/delete"))
                .setHeader("Content-Type", "application/json")
                .setHeader("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
        String deletedResponse= response.body();

        return deletedResponse;
    };


    public String getAuthToken() {
        List<HttpCookie> cookies = this.cookieManager.getCookieStore().get(URI.create(BASE_URL));
        //System.out.println(cookies);
        for (HttpCookie cookie : cookies) {
            //System.out.println("Has cookies");
            if (cookie.getName().equals("authToken")) {
                return cookie.getValue();
            }
            //System.out.println("No authToken");
        }
        //System.out.println("No cookies");
        return null;
    }





//    public String getUserList(int userID, String authToken) throws IOException, InterruptedException {
//        HashMap values = new HashMap<String, Object>() {{
//            put("userID", userID);
//            put ("authToken", authToken);
//        }};
//
//        String json = JSONUtils.toJSON(values);
//        System.out.println(json);
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(BASE_URL + "/users"))
//                .setHeader("Content-Type", "application/json")
//                .setHeader("Accept", "application/json")
//                .POST(HttpRequest.BodyPublishers.ofString(json))
//                .build();
//
//        HttpClient client = HttpClient.newHttpClient();
//        HttpResponse<String> response = client.send(request,
//                HttpResponse.BodyHandlers.ofString());
//        String responseJson = response.body();
//
//        //System.out.println(responseJson);
//
//
//        return responseJson;
//    }

}
