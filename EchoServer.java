import java.net.*;

public class EchoServer {
    public static void main(String[] args) throws Exception {
        try {
            // open new ServerSocket object with port number '8080'
            ServerSocket server = new ServerSocket(8080);

            // counter variable for tracking the number of client connections
            int counter = 0;

            // confirm server has started
            System.out.println("Server started...");

            // echo server runs until manually terminated by server user
            while (true) {
                // increment counter to account for new client connection
                counter++;

                // server accepts the client connection request
                Socket serverClient = server.accept();

                // control message for when a client connects
                System.out.println("Client No: " + counter + " connected");

                // create a new thread for new client; pass in client socket and counter number
                EchoThread newEchoThread = new EchoThread(serverClient, counter);

                // start newly created thread
                newEchoThread.start();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
