/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javademo.services;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import javademo.model.Token;

/**
 *
 * @author Lane
 */
public class WaterApiCustomerInfo {

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public static String get(String token, String clientID) {
        // json formatted data
        String value = null;
        try {
            /*
            String json = new StringBuilder()
                    .append("{")
                    .append("\"username\":\"JDB\",")
                    .append("\"password\":\"J2pmhT\"")
                    .append("}").toString();
             */
            // add json header
            HttpRequest request = HttpRequest.newBuilder()
                    //.POST(HttpRequest.BodyPublishers.ofString(json))
                    .GET()
                    .uri(URI.create("http://202.123.183.159:3000/test/api/clients/" + clientID))
                    .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
                    .header("Accept", "application/json")
                    .header("Cache-Control", "no-cache")
                    .header("Auth", token)
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // print status code
            //System.out.println(response.statusCode());
            if (HttpURLConnection.HTTP_OK != response.statusCode()) {
                value = response.body();
                //System.err.println("error " + response.statusCode());
                //System.err.println("detail " + response.headers());
                System.err.println("detail " + response.body());
            } else {
                // print response body
                //System.out.println(response.headers());
                System.out.println(response.body());
                value = response.body();
                //var token = response.headers().allValues("auth").get(0).replace("\"", "").trim();
            }
        } catch (Exception e) {
            System.err.println("Exception " + e.getMessage());
        }
        return value;
    }
}
