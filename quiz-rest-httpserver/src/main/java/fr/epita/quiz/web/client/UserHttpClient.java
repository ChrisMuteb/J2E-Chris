package fr.epita.quiz.web.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.epita.quiz.datamodel.User;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class UserHttpClient {
    private final String url;
    ObjectMapper mapper = new ObjectMapper();

    public UserHttpClient(String url) {
        this.url = url;
    }
    public List<User> getUsers() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        int responseCode = connection.getResponseCode();
        System.out.println("response code - " + responseCode);
        InputStream inputStream = connection.getInputStream();
        byte[] bytes = inputStream.readAllBytes();
        String responseText = new String(bytes);
        List<User> users = mapper.readValue(responseText, new TypeReference<List<User>>() {});
        connection.disconnect();
        return users;
    }
    public void createUser(User user) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);//activate the output
        String jsonString = mapper.writeValueAsString(user);
        byte[] bytes = jsonString.getBytes();
        connection.getOutputStream().write(bytes);
        connection.connect();
        int responseCode = connection.getResponseCode();
        connection.disconnect();
    }
    private void connectAndPut() throws IOException{
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("PUT");
        connection.connect();
        int responseCode = connection.getResponseCode();
        System.out.println("response code - " + responseCode);
        InputStream inputStream = connection.getInputStream();
        byte[] bytes = inputStream.readAllBytes();
        String responseText = new String(bytes);
        System.out.println(responseText);
    }
    private void connectAndDelete() throws IOException{
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("DELETE");
        connection.connect();
        int responseCode = connection.getResponseCode();
        System.out.println("response code - " + responseCode);
        InputStream inputStream = connection.getInputStream();
        byte[] bytes = inputStream.readAllBytes();
        String responseText = new String(bytes);
        System.out.println(responseText);
    }
}
