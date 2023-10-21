package praktikum.model;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class User {

    private String email;
    private String password;
    private String name;
    private String accessToken;
    private String refreshToken;


    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserDataForCreateUpdate() {
        Map<String, String> data = getEmailPasswordAsJson();
        data.put("name", this.name);
        return new JSONObject(data).toString();
    }

    public String getUserDataForLogin() {
        Map<String, String> data = getEmailPasswordAsJson();
        return new JSONObject(data).toString();
    }

    private Map<String, String> getEmailPasswordAsJson(){
        Map<String, String> data = new HashMap<>();
        data.put("email", this.email);
        data.put("password", this.password);
        return data;
    }

}
