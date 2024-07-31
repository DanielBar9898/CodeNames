package codeName.HttpClient.Http.Game;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

import static codeName.Configuration.GameConfig.BASE_URL;
import static codeName.Configuration.GameConfig.HTTP_CLIENT;

public class ShowTeamInfo {
    private final String RESOURCE = "/teamInfo";

    public String showTeamInfo(int gameNum , String teamName) throws IOException {
        String url = BASE_URL + RESOURCE + "?gameID=" + gameNum + "&teamName=" + teamName;
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
