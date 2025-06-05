package client;

import com.google.gson.Gson;
import exceptions.ResponseException;
import model.GameData;
import model.Request;
import model.Result;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;

public class ServerFacade {
    private final String url;

    public ServerFacade(String url) {
        this.url = url;
    }

    public void clear() {
        String path = "/db";
        makeRequest("DELETE", path, null, null, null);
    }

    public String register(String username, String password, String email) throws ResponseException {
        String path = "/user";
        Request.RegisterRequest req = new Request.RegisterRequest(username, password, email);
        Result.RegisterResult res = makeRequest("POST", path, req, Result.RegisterResult.class, null);
        return res.authToken();
    }

    public String login(String username, String password) throws ResponseException {
        String path = "/session";
        Request.LoginRequest req = new Request.LoginRequest(username, password);
        Result.LoginResult res = makeRequest("POST", path, req, Result.LoginResult.class, null);
        return res.authToken();
    }

    public void logout(String authToken) throws ResponseException{
        String path = "/session";
        Request.LogoutRequest req = new Request.LogoutRequest(authToken);
        makeRequest("DELETE", path, req, Result.LogoutResult.class, authToken);
    }

    public Collection<GameData> listGames(String authToken) throws ResponseException{
        String path = "/game";
        Request.ListGamesRequest req = new Request.ListGamesRequest(authToken);
        Result.ListGamesResult games = makeRequest("GET", path, req, Result.ListGamesResult.class, authToken);
        return games.games();
    }



    //helper functions
    private <T> T makeRequest(String method, String path, Object req, Class<T> resClass, String auth) throws ResponseException {
        try {
            URL url = (new URI(this.url + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);
            if (auth != null) http.setRequestProperty("authorization", auth);
            if (!method.equals("GET")) {
                writeBody(req, http);
            }
            http.connect();
            throwOnFail(http);

            return readBody(http, resClass);
        } catch (IOException | URISyntaxException e) {
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

    private static void writeHeader(String req, HttpURLConnection http) throws IOException {
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