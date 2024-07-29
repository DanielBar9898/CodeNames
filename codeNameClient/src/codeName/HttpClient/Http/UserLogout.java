package codeName.HttpClient.Http;

import com.google.gson.Gson;
import engine.GamePackage.Player;
import okhttp3.*;

import java.io.IOException;

import static codeName.Configuration.GameConfig.BASE_URL;

public class UserLogout {
    private final String LOGOUT_USER_RESOURCE = "/logoutUser";
    private final String LOGOUT_PLAYER_RESOURCE = "/logoutPlayer";
    private final OkHttpClient HTTP_CLIENT;

    public UserLogout() {
        this.HTTP_CLIENT = new OkHttpClient();
    }

    public String logoutUser(String username) throws IOException {
        String url = BASE_URL + LOGOUT_USER_RESOURCE;

        RequestBody body = new FormBody.Builder()
                .add("username", username)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Call call = HTTP_CLIENT.newCall(request);
        try (Response response = call.execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            return response.body().string();
        }
    }

    public String logoutPlayer(Player player) throws IOException {
        String url = BASE_URL + LOGOUT_PLAYER_RESOURCE;
        Gson gson = new Gson();
        String json = gson.toJson(player);

        RequestBody body = RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Call call = HTTP_CLIENT.newCall(request);
        try (Response response = call.execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            return response.body().string();
        }
    }
}
