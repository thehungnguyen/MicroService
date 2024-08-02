package com.hungnt.identify_service.dto.response;

public class VerifindTokenResponse {
    private boolean validToken;

    public boolean isValidToken() {
        return validToken;
    }

    public void setValidToken(boolean validToken) {
        this.validToken = validToken;
    }
}
