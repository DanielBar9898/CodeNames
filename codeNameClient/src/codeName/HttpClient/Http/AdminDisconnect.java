package codeName.HttpClient.Http;
import okhttp3.*;

import java.io.IOException;

import static codeName.Configuration.GameConfig.BASE_URL;
import static codeName.Configuration.GameConfig.HTTP_CLIENT;

public class AdminDisconnect {
    private final String RESOURCE = "/disconnect";


    public String disconnectAdmin() throws IOException{
        Request request = new Request.Builder()
                .url(BASE_URL + RESOURCE)
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
