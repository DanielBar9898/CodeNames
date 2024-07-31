package codeName.HttpClient.Http;
import okhttp3.*;
import java.io.IOException;

import static codeName.Configuration.GameConfig.BASE_URL;
import static codeName.Configuration.GameConfig.HTTP_CLIENT;

public class AdminLog {
    private static final String ADMIN_RESOURCE= "/admin";


    public String adminLogin(String adminName) throws IOException {
        String url = BASE_URL + ADMIN_RESOURCE;
        RequestBody formBody = new FormBody.Builder()
                .add("action", "login")
                .add("adminName", adminName)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        Call call = HTTP_CLIENT.newCall(request);
        try (Response response = call.execute()) {
            String responseBody = response.body().string();
            if (response.code() == 409) {
                return "An admin is already logged in, please try again later.";
            }
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response + " with message: " + responseBody);
            }
            return responseBody;
        }
    }

    public String adminLogout() throws IOException {
        String url = BASE_URL + ADMIN_RESOURCE;
        RequestBody formBody = new FormBody.Builder()
                .add("action", "logout")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        Call call = HTTP_CLIENT.newCall(request);
        try (Response response = call.execute()) {
            String responseBody = response.body().string();
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response + " with message: " + responseBody);
            }
            return responseBody;
        }
    }
}

