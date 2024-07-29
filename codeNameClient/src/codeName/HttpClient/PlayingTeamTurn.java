package codeName.HttpClient;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

import static codeName.Configuration.GameConfig.BASE_URL;
import static codeName.Configuration.GameConfig.HTTP_CLIENT;

public class PlayingTeamTurn {
    private final String RESOURCE = "/playingTurn";
    public String playingTeamTurn(int gameNum) throws IOException {
        String url = BASE_URL + RESOURCE + "?gameID=" + gameNum;
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
