import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpServerTest {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(80), 0);
        HttpContext context = server.createContext("/test");
        context.setHandler(ex -> {
            String requestMethod = ex.getRequestMethod();
            switch (requestMethod){
                case "GET":
                    System.out.println("GET endpoint");
                    break;
                case "POST":
                    System.out.println("POST endpoint");
                    break;
                case "PUT":
                    System.out.println("PUT endpoint");
                    break;
                case "DELETE":
                    System.out.println("DELETE endpoint");
                    break;
            }
        });
        server.start();
        System.out.println("Ready for inputs");
        server.stop(2);
    }
}
