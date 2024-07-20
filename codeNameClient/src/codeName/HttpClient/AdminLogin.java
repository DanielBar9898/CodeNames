package codeName.HttpClient;
import engine.users.UserManager;
import okhttp3.*;
import java.io.IOException;

import static codeName.Configuration.GameConfig.BASE_URL;
import static codeName.Configuration.GameConfig.HTTP_CLIENT;

public class AdminLogin {
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";

    public void loginAdmin() {
        // Perform login request using OkHttp
        RequestBody body = new FormBody.Builder()
                .add("username", ADMIN_USERNAME)
                .add("password", ADMIN_PASSWORD)
                .build();

        Request request = new Request.Builder()
                .url(BASE_URL + "/login")  // Replace with your login endpoint
                .post(body)
                .build();

        try {
            Response response = HTTP_CLIENT.newCall(request).execute();
            if (response.isSuccessful()) {
                // Handle successful login response
                String responseBody = response.body().string();
                System.out.println("Admin logged in successfully: " + responseBody);
            } else {
                // Handle unsuccessful login
                System.out.println("Failed to login admin. HTTP status code: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

