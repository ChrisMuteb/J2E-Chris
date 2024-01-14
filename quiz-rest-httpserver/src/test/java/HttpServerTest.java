import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import fr.epita.quiz.datamodel.User;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class HttpServerTest {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException, TimeoutException {
        HttpServer server = HttpServer.create(new InetSocketAddress(80), 0);
        int processors = Runtime.getRuntime().availableProcessors() / 2;
        ExecutorService executor = Executors.newFixedThreadPool(processors);
        server.setExecutor(executor);
        HttpContext context = server.createContext("/test");
        context.setHandler(ex -> {
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
        });
        server.start();

        System.out.println("Ready for inputs");
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Future<?>> futures = new ArrayList<>();
        for(int i=0; i<100; i++){
            System.out.println("client - starting task - i = " + i);
             Future<?> future = executorService.submit(()->{
                 System.out.println(Thread.currentThread().getId());
                 System.out.println(Thread.currentThread().getState());
                try{
                    connectAndGet();
                    connectAndPost();
//        connectAndPut();
//        connectAndDelete();
                }catch (IOException ioe){
                    ioe.printStackTrace();
                }
            });
             futures.add(future);
        }

        for (Future future: futures)
            future.get();
        server.stop(2);
    }
    private static void connectAndGet() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:80/test").openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        int responseCode = connection.getResponseCode();
        System.out.println("response code - " + responseCode);
        InputStream inputStream = connection.getInputStream();
        byte[] bytes = inputStream.readAllBytes();
        String responseText = new String(bytes);
        System.out.println(responseText);
        ObjectMapper mapper = new ObjectMapper();
        List<User> users = mapper.readValue(responseText, new TypeReference<List<User>>() {});
        System.out.println("The users list size is: " + users.size());
        System.out.println("first user: " + users.get(0));
    }
    private static void connectAndPost() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:80/test").openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);//activate the output
        User user = new User("Amos", "Lyon", "563");
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(user);
        byte[] bytes = jsonString.getBytes();
        connection.getOutputStream().write(bytes);
        connection.connect();

        int responseCode = connection.getResponseCode();
        System.out.println("response code - " + responseCode);
        System.out.println(new String(connection.getInputStream().readAllBytes()));
        connection.disconnect();

    }
    private static void connectAndPut() throws IOException{
        HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:80/test").openConnection();
        connection.setRequestMethod("PUT");
        connection.connect();
        int responseCode = connection.getResponseCode();
        System.out.println("response code - " + responseCode);
        InputStream inputStream = connection.getInputStream();
        byte[] bytes = inputStream.readAllBytes();
        String responseText = new String(bytes);
        System.out.println(responseText);
    }
    private static void connectAndDelete() throws IOException{
        HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:80/test").openConnection();
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
