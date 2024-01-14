import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;

public class HttpServerTest {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(80), 0);
        HttpContext context = server.createContext("/test");
        context.setHandler(ex -> {
            String requestMethod = ex.getRequestMethod();
            switch (requestMethod){
                case "GET":
                    System.out.println("GET endpoint");
                    byte[] bytes = "Hello from get!".getBytes();
                    ex.sendResponseHeaders(200, bytes.length);
                    ex.getResponseBody().write(bytes);
                    break;
                case "POST":
                    System.out.println("POST endpoint");
                    byte[] bytesPost = "Hello from post!".getBytes();
                    ex.sendResponseHeaders(201, bytesPost.length);
                    ex.getResponseBody().write(bytesPost);
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
        connectAndGet();
//        connectAndPost();
//        connectAndPut();
//        connectAndDelete();
        server.stop(2);
    }
    private static void connectAndGet() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:80/test").openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        int responseCode = connection.getResponseCode();
        System.out.println("response core - " + responseCode);
        InputStream inputStream = connection.getInputStream();
        byte[] bytes = inputStream.readAllBytes();
        String responseText = new String(bytes);
        System.out.println(responseText);
    }
    private static void connectAndPost() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:80/test").openConnection();
        connection.setRequestMethod("POST");
        connection.connect();
        int responseCode = connection.getResponseCode();
        System.out.println("response code - " + responseCode);
        InputStream inputStream = connection.getInputStream();
        byte[] bytes = inputStream.readAllBytes();
        String responseText = new String(bytes);
        System.out.println(responseText);
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
