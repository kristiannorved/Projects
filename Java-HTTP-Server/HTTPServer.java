import java.net.ServerSocket;

public class HTTPServer {
    public static void main(String[] args) throws Exception {
    final ServerSocket server = new ServerSocket(8080); // constant variable for server
    System.out.println("Listening for connection on port 8080 ....");
    while (true){
      // spin forever
    }
  }
}