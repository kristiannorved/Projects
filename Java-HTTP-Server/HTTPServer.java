import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HTTPServer {
    public static void main(String[] args) throws IOException {
        // start server on port 8080
        ServerSocket server = new ServerSocket(8080);
        System.out.println("Listening for connection on port 8080..."); 

        while (true) {
            try {
                // accept connection from a client
                Socket socket = server.accept();
                InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream();

                // load HTML file from file directory
                StringBuilder contentBuilder = new StringBuilder();
                try {
                    BufferedReader webpage = new BufferedReader(new FileReader("index.html"));
                    String str;
                    while ((str = webpage.readLine()) != null) {
                        contentBuilder.append(str);
                    }
                    webpage.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String html = contentBuilder.toString();

                // construct valid HTML response message
                final String CRLF = "\r\n"; // 13, 10
                String response = 
                    "HTTP/1.1 200 OK" + CRLF + // status line: HTTP_VERSION RESPONSE_CODE RESPONSE_MESSAGE
                    "Content-Length: " + html.getBytes().length + CRLF + // header
                    CRLF + 
                    html + 
                    CRLF + CRLF;

                // write HTML message to the client
                out.write(response.getBytes());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}