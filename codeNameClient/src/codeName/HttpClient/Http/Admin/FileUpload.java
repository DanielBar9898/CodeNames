package codeName.HttpClient.Http.Admin;
import okhttp3.*;

import java.io.File;
import java.io.IOException;

import static codeName.Configuration.GameConfig.BASE_URL;
import static codeName.Configuration.GameConfig.HTTP_CLIENT;

public class FileUpload {
    private final String RESOURCE = "/loadXmlFile";
    private File file;
    private String filePath;
    public FileUpload(String filePath){
        this.filePath = filePath;
        this.file = new File(filePath);
    }
    public String uploadFile() throws IOException{
        RequestBody body =
                new MultipartBody.Builder().addFormDataPart("filePath" , filePath)
                        .addFormDataPart("file", file.getName(), RequestBody.create(file, MediaType.parse("text/plain")))
                        .build();

        Request request = new Request.Builder()
                .url(BASE_URL + RESOURCE)
                .post(body)
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
