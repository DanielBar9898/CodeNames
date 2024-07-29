package codeName.HttpClient.Http;

import okhttp3.*;

import java.io.IOException;

import static codeName.Configuration.GameConfig.BASE_URL;
import static codeName.Configuration.GameConfig.HTTP_CLIENT;

public class UserNameList {
    private final String RESOURCE = "/login";

    public String addUserName(String userName) throws IOException {
        String url = BASE_URL + RESOURCE + "?username=" + userName + "&action=add";
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

    public String removeUserName(String userName) throws IOException {
        String url = BASE_URL + RESOURCE + "?username=" + userName + "&action=remove";
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
