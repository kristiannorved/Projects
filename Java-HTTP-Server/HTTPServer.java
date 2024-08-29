import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

public class HTTPServer {
    public static void main(String[] args) throws IOException {
        // start server on port 8080
        ServerSocket server = new ServerSocket(8080);
        System.out.println("Opening server on localhost:8080");

        while (true) {
            // accept connection from a client
            Socket socket = server.accept();

            // if connection is stable, read and write from client
            try (OutputStream out = socket.getOutputStream()) {
                InputStream in = socket.getInputStream();
                var line = new BufferedReader(new InputStreamReader(in)).readLine();
                String file = line.substring(5, line.length() - 9);
                if (file.equals("")) {
                    file = "index.html";
                } 
                
                // load script file from file directory
                StringBuilder contentBuilder = new StringBuilder();
                try {
                    BufferedReader webpage = new BufferedReader(new FileReader(file));
                    String str;
                    while ((str = webpage.readLine()) != null) {
                        contentBuilder.append(str);
                        contentBuilder.append("\n");
                    }
                    webpage.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String content = contentBuilder.toString();

                // construct valid HTML response message
                final String CRLF = "\r\n"; // 13, 10
                String response = 
                    "HTTP/1.1 200 OK" + CRLF + // status line: HTTP_VERSION RESPONSE_CODE RESPONSE_MESSAGE
                    "Content-Length: " + content.getBytes().length + CRLF + // header
                    CRLF + 
                    content + 
                    CRLF + CRLF;

                // write HTML message to the client
                out.write(response.getBytes());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}