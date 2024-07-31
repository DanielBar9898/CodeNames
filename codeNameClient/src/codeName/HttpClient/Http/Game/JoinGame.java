package codeName.HttpClient.Http.Game;

import okhttp3.*;

import java.io.IOException;

import static codeName.Configuration.GameConfig.BASE_URL;
import static codeName.Configuration.GameConfig.HTTP_CLIENT;

public class JoinGame {
    private final String RESOURCE = "/joinGame";

    public String joinGame(String username, int gameNumber, int teamNumber, String role) throws IOException {
        String url = BASE_URL + RESOURCE + "?username=" + username + "&gameNumber=" + gameNumber + "&teamNumber=" + teamNumber + "&role=" + role;
        Request request = new Request.Builder()
                .url(url)
                .get()
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
