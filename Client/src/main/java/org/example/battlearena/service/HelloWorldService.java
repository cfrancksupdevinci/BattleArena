package org.example.battlearena.service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class HelloWorldService {
    private String URL;

    public HelloWorldService(String URL) {
        this.URL = URL;
    }

    public void GetHelloWorld() {
    try {
        URL url = new URL(this.URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        } else {
            Scanner scanner = new Scanner(url.openStream());
            StringBuilder inline = new StringBuilder();
            while (scanner.hasNext()) {
                inline.append(scanner.nextLine());
            }
            scanner.close();

            JSONParser parser = new JSONParser();
            JSONObject dataObj = (JSONObject) parser.parse(inline.toString());
            System.out.println(dataObj.toJSONString());
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    }

}