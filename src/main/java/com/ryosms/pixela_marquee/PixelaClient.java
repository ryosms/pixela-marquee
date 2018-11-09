/*
 * Copyright 2018 ryosms
 */

package com.ryosms.pixela_marquee;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * @author ryosms
 */
class PixelaClient {

    PixelaResponse get(String url) throws IOException {
        HttpURLConnection connection = null;
        try {
            connection = createConnection(url);
            connection.setRequestMethod("GET");
            connection.connect();

            final int status = connection.getResponseCode();
            InputStream responseStream;
            if (status == HttpURLConnection.HTTP_OK) {
                responseStream = connection.getInputStream();
            } else {
                responseStream = connection.getErrorStream();
            }

            StringBuilder responseBody = new StringBuilder();
            try (InputStreamReader reader = new InputStreamReader(responseStream, StandardCharsets.UTF_8);
                 BufferedReader bufferedReader = new BufferedReader(reader)) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    responseBody.append(line).append("\n");
                }
            }
            responseStream.close();

            return new PixelaResponse(status, responseBody.toString());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }


    private HttpURLConnection createConnection(String endpoint) throws IOException {
        HttpURLConnection connection;
        // TODO: proxy settings

        connection = (HttpURLConnection) new URL(endpoint).openConnection();
        connection.setInstanceFollowRedirects(false);

        return connection;
    }


}
