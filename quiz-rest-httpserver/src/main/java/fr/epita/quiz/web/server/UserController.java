package fr.epita.quiz.web.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import fr.epita.quiz.datamodel.User;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class UserController implements HttpHandler {

    @Override
    public void handle(HttpExchange ex) throws IOException {
        String requestMethod = ex.getRequestMethod();
        ObjectMapper mapper = new ObjectMapper();

        switch (requestMethod){
            case "GET":
                System.out.println("GET endpoint");
                User user = new User("Chris", "Paris", "123");
                User user1 = new User("Elie", "Lyon", "456");
                List<User> users = Arrays.asList(user, user1);
                String jsonString = mapper.writeValueAsString(users);
                byte[] bytes = jsonString.getBytes();
                ex.sendResponseHeaders(200, bytes.length);
                ex.getResponseBody().write(bytes);
                break;
            case "POST":
                System.out.println("POST endpoint");
                User userToBeCreated = mapper.readValue(ex.getRequestBody(), User.class);
                System.out.println(userToBeCreated);
                byte[] response = "user created with id 1".getBytes();
                ex.sendResponseHeaders(201, response.length);
                ex.getResponseBody().write(response);
                break;
            case "PUT":
                System.out.println("PUT endpoint");
                byte[] bytesPut = "Hello from put!".getBytes();
                ex.sendResponseHeaders(202, bytesPut.length);
                ex.getResponseBody().write(bytesPut);
                break;
            case "DELETE":
                System.out.println("DELETE endpoint");
                byte[] bytesDelete = "Hello from delete!".getBytes();
                ex.sendResponseHeaders(201, bytesDelete.length);
                ex.getResponseBody().write(bytesDelete);
                break;
        }
    }
}
