package codeName.HttpClient.Http;

import codeName.GameMain.AppStructure.ChatAppMainController;
//import chat.client.util.http.HttpClientUtil;
import codeName.HttpClient.Constants;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import codeName.HttpClient.Utills.HttpClientUtil;
import java.io.IOException;


public class LoginController {



    @FXML
    public Label errorMessageLabel;

    private ChatAppMainController chatAppMainController;

    private final StringProperty errorMessageProperty = new SimpleStringProperty();




    public void login(String userName) {

        if (userName.isEmpty()) {
            errorMessageProperty.set("User name is empty. You can't login with empty user name");
            return;
        }

        //noinspection ConstantConditions
        String finalUrl = HttpUrl
                        .parse(Constants.LOGIN_PAGE)
                        .newBuilder()
                        .addQueryParameter("username", userName)
                        .build()
                        .toString();

        updateHttpStatusLine("New request is launched for: " + finalUrl);

        HttpClientUtil.runAsync(finalUrl, new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Platform.runLater(() ->
                        errorMessageProperty.set("Something went wrong: " + e.getMessage())
                );
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.code() != 200) {
                    String responseBody = response.body().string();
                    Platform.runLater(() ->
                            errorMessageProperty.set("Something went wrong: " + responseBody)
                    );
                } else {
                    Platform.runLater(() -> {
                            chatAppMainController.updateUserName(userName);
                           // chatAppMainController.switchToChatRoom();
                    });
                }
            }
        });
    }



  private void updateHttpStatusLine(String data) {
      // chatAppMainController.updateHttpLine(data);
      errorMessageLabel.setText(data); // delete!!!!!!!!!!!!!!!!!
  }}
/*
    public void setChatAppMainController(ChatAppMainController chatAppMainController) {
        this.chatAppMainController = chatAppMainController;
    }*/

