package codeName.HttpClient.Http;

import engine.GamePackage.Player;
import okhttp3.*;

import java.io.IOException;

import static codeName.Configuration.GameConfig.BASE_URL;
import static codeName.Configuration.GameConfig.HTTP_CLIENT;

public class PlayTurn {
    private String RESOURCE = "/playTurn";

    public String playTurnGuesser(Player player , String guess) throws IOException{
        RESOURCE+="Guesser";
        String url = BASE_URL + RESOURCE +"?gameNumber" + player.getSerialGameNumber() + "&name" + player.getName() +
                "&guess" + guess;

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
    public String playTurnDefiner(Player player , String hint , int numOfWords) throws IOException{
        RESOURCE+="Definer";
        String url = BASE_URL + RESOURCE +"?gameNumber" + player.getSerialGameNumber() + "&name" + player.getName() +
                "&hint" + hint + "&numOfWords" + numOfWords;

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
