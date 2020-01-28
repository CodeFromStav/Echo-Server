import java.net.*;
import java.io.*;

// Test class to verify server-client connection functionality
public class Client {

    public static void main(String[] args) throws Exception {

        try {
            Socket clientSock = new Socket("127.0.0.1", 8080); // open up connection on right port
            DataInputStream inStream = new DataInputStream(clientSock.getInputStream()); // Setup streams for incoming and outgoing data
            DataOutputStream outStream = new DataOutputStream(clientSock.getOutputStream());
            BufferedReader buffRead = new BufferedReader(new InputStreamReader(System.in));
            String clientMessage = "", serverMessage = ""; //add text here?

            //Examples of shutdown sequences include: 'quit', 'q123u$#i66t', 'q0u0i0t', 'q u i t'
            while (!checkState(clientMessage)) {
                System.out.println("Enter text :");
                clientMessage = buffRead.readLine();
                outStream.writeUTF(clientMessage);
                outStream.flush();
                serverMessage = inStream.readUTF();
                System.out.println(serverMessage);
            }

            // control message for when the user enters the word 'quit'
            System.out.println("Client has entered the 'quit' keyword; the connection has been closed.");

            //close I/O stream, bufferReader, and clientSocket
            outStream.close();
            outStream.close();
            buffRead.close();
            clientSock.close();

        } catch (Exception e) {
            System.out.println("An Exception");
        }
    }
    // checks the state of the client's message, if quit is found the connection is terminated
    private static boolean checkState(String state) {

        // removes all spaces in the string 'state' to make it easier to look for 'quit'
        state = state.replaceAll("[^a-zA-Z ]", "");

        // removes all spaces in the string 'state' to make it easier to look for 'quit'
        state = state.replaceAll("\\s+","");

        return state.contains("quit");
    }
}
