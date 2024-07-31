package codeName.HttpClient.Http;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import engine.chatPackage.SingleChatEntry;
import engine.GamePackage.Player;
import okhttp3.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import static codeName.Configuration.GameConfig.BASE_URL;
import static codeName.Configuration.GameConfig.HTTP_CLIENT;

public class ChatClient {
    private static final String CHAT_RESOURCE = "/chat";

    public void sendTeamMessage(Player player, String message) throws IOException {
        String url = BASE_URL + CHAT_RESOURCE;
        RequestBody body = new FormBody.Builder()
                .add("action", "addTeamMessage")
                .add("message", message)
                .add("username", player.getName())
                .add("role", player.getRole().name())
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
        }
    }

    public void sendDefinerMessage(Player player, String message) throws IOException {
        String url = BASE_URL + CHAT_RESOURCE;
        RequestBody body = new FormBody.Builder()
                .add("action", "addDefinerMessage")
                .add("message", message)
                .add("username", player.getName())
                .add("role", player.getRole().name())
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
        }
    }

    public List<SingleChatEntry> getTeamMessages(int fromIndex) throws IOException {
        String url = BASE_URL + CHAT_RESOURCE + "?action=getTeamMessages&fromIndex=" + fromIndex;
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Call call = HTTP_CLIENT.newCall(request);
        try (Response response = call.execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            Type listType = new TypeToken<List<SingleChatEntry>>() {}.getType();
            return new Gson().fromJson(response.body().string(), listType);
        }
    }

    public List<SingleChatEntry> getDefinerMessages(int fromIndex) throws IOException {
        String url = BASE_URL + CHAT_RESOURCE + "?action=getDefinerMessages&fromIndex=" + fromIndex;
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Call call = HTTP_CLIENT.newCall(request);
        try (Response response = call.execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            Type listType = new TypeToken<List<SingleChatEntry>>() {}.getType();
            return new Gson().fromJson(response.body().string(), listType);
        }
    }

    public List<SingleChatEntry> getNewMessages(String chatType, int fromIndex) throws IOException {
        String url = BASE_URL + CHAT_RESOURCE + "?action=getNewMessages&chatType=" + chatType + "&fromIndex=" + fromIndex;
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Call call = HTTP_CLIENT.newCall(request);
        try (Response response = call.execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            Type listType = new TypeToken<List<SingleChatEntry>>() {}.getType();
            return new Gson().fromJson(response.body().string(), listType);
        }
    }
}
