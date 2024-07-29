package codeName.HttpClient;

import okhttp3.*;

import java.io.IOException;

import static codeName.Configuration.GameConfig.BASE_URL;
import static codeName.Configuration.GameConfig.HTTP_CLIENT;

public class DisplayHint {
    private final String RESOURCE = "/displayHint";

    public String displayHint (String teamName)  throws IOException {
        String url = BASE_URL + RESOURCE + "?teamName=" + teamName;
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
