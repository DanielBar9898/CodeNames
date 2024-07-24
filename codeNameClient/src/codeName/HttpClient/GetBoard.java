package codeName.HttpClient;

import okhttp3.*;

import java.io.IOException;

import static codeName.Configuration.GameConfig.BASE_URL;
import static codeName.Configuration.GameConfig.HTTP_CLIENT;

public class GetBoard {
    private final String RESOURCE = "/getBoard";

    public String getBoard(int gameNumber) throws IOException {
        String url = BASE_URL + RESOURCE + "?gameNumber=" + gameNumber;
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
