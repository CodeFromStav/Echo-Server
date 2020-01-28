import java.net.*;
import java.io.*;

/* Class to sepearate instances of server and client connections */
public class EchoThread implements Runnable {
    // running thread associated with client
    Thread currentThread;
    // socket associated with client's connection
    Socket clientSocket;
    // number associated to client, e.g. client 1, client 2, etc.
    int clientNumber;
    // small state machine used to look for the character sequence 'quit'
    String stateMachine = "";

    // echo thread constructor to account for new client connection
    EchoThread(Socket inSocket, int counter) {
        clientSocket = inSocket;
        clientNumber = counter;
    }

    /* Runnable portion of the class, opens up a connection between the client and
    server */
    public void run() {
        try {
            // the variable holding a reference to the input stream pouring in characters from the client
            DataInputStream fromClient = new DataInputStream(clientSocket.getInputStream());
            // the variable holding a reference to the output stream that is sending characters back to the client
            DataOutputStream toClient = new DataOutputStream(clientSocket.getOutputStream());
            // control message for when data streams are created
            System.out.println("Streams created");
            String charFromClient = "", serverMSG = "";

            // continue connection until key word 'quit' is sent by client
            while (!checkState(stateMachine)) {
                // read client messages. any character received will only be sent back, if that character is a small or
                //      capital letter of the English alphabet
                charFromClient = fromClient.readUTF().replaceAll("[^a-zA-Z ]", "").
                        replaceAll("\\s", "");
                // control message to notify server user that the data has been recieved
                System.out.println("Data received");
                // Add input to the state machine
                stateMachine += charFromClient.toLowerCase();
                // Send input back to the client
                sendMSG(charFromClient.toCharArray(), toClient);
            }

            // control message for when the user enters the word 'quit'
            System.out.println("Client has entered the 'quit' keyword; the connection has been closed.");
            // close I/O streams and sockets
            fromClient.close();
            toClient.close();
            clientSocket.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }
        // control message for when a client closes/exits their socket
        finally {
            System.out.println("Client " + clientNumber + " exited");
        }
    }

    /* Creates a new thread to start a server-client connection */
    public void start() {
        if (currentThread == null) {

            // creating a new thread for new client
            currentThread = new Thread(this);
            currentThread.start();
        }
    }

    /* Checks to see if the server has recieved the characters 'quit' to quit the connection */
    public boolean checkState(String state) {

        // removes all spaces in the string 'state' to make it easier to look for 'quit'
        state = state.replaceAll("\\s+", "");
        return state.contains("quit");
    }

    /* Echo's back the message sent from the client back to the client 'char by char'*/
    public void sendMSG(char[] clientMSG, DataOutputStream outStream) {
        // builds the string as a list of chars to the client
        StringBuilder charByChar = new StringBuilder();

        // for loop to create and store character by character for serverMSG variable
        for (char currChar : clientMSG) {
            String serverMSG = "From Server to Client " + clientNumber + " " + charByChar + "\n";
            charByChar.append(currChar).append("\n");
        }

        String serverMSG = "From Server to Client " + clientNumber + ", here's your data: \n" + charByChar;

        try {
            // control message to user in regard to the chars that have been input thus far
            outStream.writeUTF(serverMSG + "\nChars in sequence so far: " + stateMachine);
            // print out control message
            outStream.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
