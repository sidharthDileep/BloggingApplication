package online.lahiru.sprinngbotrestapi.payload;

public class JWTAuthResponse {
    private String accessToken;
    private String tokentype="Bearer";

    public JWTAuthResponse(String accessToken) {
        this.accessToken = accessToken;

    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokentype() {
        return tokentype;
    }

    public void setTokentype(String tokentype) {
        this.tokentype = tokentype;
    }

}
