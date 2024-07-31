package codeName.HttpClient.Http.Game;

import okhttp3.*;

import java.io.IOException;

import static codeName.Configuration.GameConfig.BASE_URL;
import static codeName.Configuration.GameConfig.HTTP_CLIENT;

public class PendingGames {
    private final String RESOURCE = "/pendingGame";


    public String showPendingGames() throws IOException {
        return executeRequest(BASE_URL + RESOURCE);
    }


    public String selectPendingGame(int gameNumber) throws IOException {
        String url = BASE_URL + RESOURCE + "?gameNumber=" + gameNumber;
        return executeRequest(url);
    }


    private String executeRequest(String url) throws IOException {
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
