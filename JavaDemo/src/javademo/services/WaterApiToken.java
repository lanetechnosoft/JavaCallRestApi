/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javademo.services;

import java.net.URI;
import java.net.http.*;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import javademo.model.Token;
//import javademo.model.Token;

/**
 *
 * @author Lane
 */
public class WaterApiToken {

    public WaterApiToken() {
    }

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public static Token getToken() {
        // json formatted data
        Token value = null;
        try {

            String json = new StringBuilder()
                    .append("{")
                    .append("\"username\":\"JDB\",")
                    .append("\"password\":\"J2pmhT\"")
                    .append("}").toString();

            // add json header
            HttpRequest request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .uri(URI.create("http://202.123.183.159:3000/test/api/login"))
                    .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
                    .header("Content-Type", "application/json")
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // print status code
            //System.out.println(response.statusCode());
            if (response.statusCode() != 200) {
                value = new Token();
                value.setStatus(response.statusCode());
                value.setHeader(response.headers().toString());
                value.setBody(response.body());
                //System.err.println("error " + response.statusCode());
                //System.err.println("detail " + response.body());
            }
            // print response body
            //System.out.println(response.headers().allValues("auth").get(0).replace("\"", "").trim());

            var token = response.headers().allValues("auth").get(0).replace("\"", "").trim();
            value = new Token();
            value.setStatus(response.statusCode());
            value.setHeader(token);
            value.setBody(response.body());
        } catch (Exception e) {
            value = new Token();
            value.setStatus(500);
            value.setHeader("Internal Server Error");
            value.setBody("error " + e.getMessage());
            System.err.println("Exception " + e.getMessage());
        }
        return value;
    }

}
