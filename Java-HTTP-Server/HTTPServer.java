import java.io.IOException;

/**
 * Driver class for our HTTPServer 
 */
public class HTTPServer {
    public static void main(String[] args) throws IOException {
        // start server on port 8080
        int port = 8080;
        ServerThread thread = new ServerThread(port);
        thread.start();
    }
}