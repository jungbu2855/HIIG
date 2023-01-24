package com.example.hiig;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;

import org.json.*;

public class OpenAPIInterface {
    static public JSONObject get(String _svc, String _key, String _ext) throws IOException, JSONException {
        String urlBuilder = "http://swopenapi.seoul.go.kr:8088";
        urlBuilder += "/" +  URLEncoder.encode(_key,"UTF-8");
        urlBuilder += "/" +  URLEncoder.encode("json","UTF-8");
        urlBuilder += "/" + URLEncoder.encode(_svc,"UTF-8");
        urlBuilder += "/" + URLEncoder.encode("1","UTF-8");
        urlBuilder += "/" + URLEncoder.encode("5","UTF-8");
        urlBuilder += "/" + URLEncoder.encode(_ext,"UTF-8");

        URL url = new URL(urlBuilder);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/xml");

        int rc = conn.getResponseCode();
        BufferedReader rd = new BufferedReader(
                new InputStreamReader(
                        (rc >= 200 && rc < 300) ? conn.getInputStream() : conn.getErrorStream()
                )
        );

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        return new JSONObject(sb.toString());
    }
}