import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HTTPServer {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8080);
        System.out.println("Listening for connection on port 8080..."); 
        while (true) {
            try {
                Socket socket = server.accept();
                
                InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream();

                String html = "<html><head><title>Simple HTTP Java Server</title></head><body><hl>This page was served using my Simple Java HTTP server</hl></body></html>";

                final String CRLF = "\r\n"; // 13, 10

                String response = 
                    "HTTP/1.1 200 OK" + CRLF + // Status Line: HTTP_VERSION RESPONSE_CODE RESPONSE_MESSAGE
                    "Content-Length: " + html.getBytes().length + CRLF + // HEADER
                    CRLF + 
                    html + 
                    CRLF + CRLF;
                out.write(response.getBytes());
                
                /*
                in.close();
                out.close();
                socket.close();
                server.close();
                */

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}