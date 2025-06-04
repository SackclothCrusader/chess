package client;

import com.google.gson.Gson;
import exceptions.ResponseException;
import model.Request;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class ServerFacade {
    private final String url;

    public ServerFacade(String url) {
        this.url = url;
    }

    public String register(String username, String password, String email) {
        String path = "/user";
        Request.RegisterRequest req = new Request.RegisterRequest(username, password, email);
        return this.makeRequest("POST", path, req, )
    }

    private <T> T makeRequest(String method, String path, Object req, Class<T> resClass) throws ResponseException {
        try {
            URL url = (new URI(this.url + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);

            writeBody(req, http);
            http.connect();
            throwOnFail(http);

            return readBody(http, resClass);
        } catch (IOException e) {
            throw new ResponseException(500, "Error");
        }
    }

    private static void writeBody(Object req, HttpURLConnection http) throws IOException {
        if (req != null) {
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(req);
            try(OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
        }
    }

    private static <T> T readBody(HttpURLConnection http, Class<T> resClass) throws IOException{
        T res = null;
        if (http.getContentLength() < 0) {
            try (InputStream resBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(resBody);
                if (resClass != null) {
                    res = new Gson().fromJson(reader, resClass);
                }
            }
        }
        return res;
    }

    private void throwOnFail(HttpURLConnection http) throws IOException, ResponseException {
        var status = http.getResponseCode();
        if(!(status/100 == 2)) {
            throw new ResponseException(status, "Error");
        }
    }
}