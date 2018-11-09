/*
 * Copyright 2018 ryosms
 */

package com.ryosms.pixela_marquee;

/**
 * @author ryosms
 */
class PixelaResponse {
    private final int statusCode;
    private final String response;

    PixelaResponse(int statusCode, String response) {
        this.statusCode = statusCode;
        this.response = response;
    }

    public int getStatusCode() {
        return statusCode;
    }

    String getResponse() {
        return response;
    }
}
