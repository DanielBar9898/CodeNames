package codeName.HttpClient.Http;

import okhttp3.*;

import java.io.IOException;

import static codeName.Configuration.GameConfig.BASE_URL;
import static codeName.Configuration.GameConfig.HTTP_CLIENT;

public class SelectTeam {
    private final String RESOURCE = "/selectTeam";

    public String selectTeam(int gameNumber, int teamNumber) throws IOException {
        String url = BASE_URL + RESOURCE + "?gameNumber=" + gameNumber + "&teamNumber=" + teamNumber;
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
