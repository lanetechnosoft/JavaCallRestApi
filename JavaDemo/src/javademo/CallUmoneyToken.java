/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javademo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Base64;
import java.util.HashMap;
import java.util.stream.Collectors;
import javademo.model.Token;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 *
 * @author Lane
 */
public class CallUmoneyToken {

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .authenticator(new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(
                            "bank-jdb-clientid",
                            "bank-jdb-clients3cr3t".toCharArray());
                }

            })
            //.version(HttpClient.Version.HTTP_2)
            //.connectTimeout(Duration.ofSeconds(10))
            .build();
    private static final String uri = "http://183.182.100.174:8083/ewallet/token";

    public static void main(String[] args) {
        Token value = null;
        try {

            HashMap<String, String> parameters = new HashMap<>();
            parameters.put("username", "bank-jdb");
            parameters.put("password", "bank-jdb-pw");
            parameters.put("grant_type", "password");
            String form = parameters.keySet().stream()
                    .map(key -> key + "=" + URLEncoder.encode(parameters.get(key), StandardCharsets.UTF_8))
                    .collect(Collectors.joining("&"));
            //System.out.println(form);
            // add json header
            HttpRequest request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(form))
                    .uri(URI.create(uri))
                    .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    //.header("Authorization", "Basic YmFuay1qZGItY2xpZW50aWQ6YmFuay1qZGItY2xpZW50czNjcjN0")
                    .build();
            /*
            HttpResponse<?> resp = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(resp.statusCode() + resp.body().toString());
             */

            HttpResponse<String> response = (HttpResponse<String>) httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // print status code
            //System.out.println(response.statusCode());
            // print response body
            //System.out.println(response.body());
            JsonReader reader = new JsonReader(new StringReader(response.body()));
            reader.beginObject();
            //if (reader.hasNext()) {
            while (reader.hasNext()) {
                if (reader.nextName().equals("access_token")) {
                    //if (token.equals("access_token")) {
                    //response = reader.nextString();
                    String res = reader.nextString();
                    System.out.println(res);
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();
            reader.close();

        } catch (Exception e) {

            System.err.println("Exception " + e.getMessage());
        }
    }

    private static Reader traceReader(final Reader reader) {
        return new Reader() {
            @Override
            public int read(final char[] buffer, final int offset, final int length)
                    throws IOException {
                final int read = reader.read(buffer, offset, length);
                if (read != -1) {
                    // or any other appropriate tracing output here
                    System.out.print(new String(buffer, offset, read));
                    System.out.flush();
                }
                return read;
            }

            @Override
            public void close()
                    throws IOException {
                reader.close();
            }
        };
    }
}
