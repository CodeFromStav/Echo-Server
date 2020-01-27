import java.net.*;
import java.io.*;

public class EchoThread implements Runnable {
    Thread currentThread;
    Socket clientSocket;
    // number associated to client, e.g. client 1, client 2, etc.
    int clientNumber;
    // small state machine used to look for the character sequence 'quit'
    String stateMachine = "";

    EchoThread(Socket inSocket, int counter) {
        clientSocket = inSocket;
        clientNumber = counter;
    }

    public void run() {
        try {
            // the variable holding a reference to the input stream pouring in characters from the client
            DataInputStream fromClient = new DataInputStream(clientSocket.getInputStream());

            // the variable holding a reference to the output stream that is sending characters back to the client
            DataOutputStream toClient = new DataOutputStream(clientSocket.getOutputStream());

            // control message for when data streams are created
            System.out.println("Streams created");
            String clientMSG = "", serverMSG = "";

            // continue connection until key word 'quit' is sent by client
            while (!checkState(stateMachine)) {
                // any character received will only be sent back, if that character is a small or capital letter of the English
                //    alphabet
                clientMSG = fromClient.readUTF().replaceAll("[^a-zA-Z ]", ""); //read client messages
                System.out.println("Data received");
                stateMachine += clientMSG.toLowerCase(); //Add input to the state machine
                sendMSG(clientMSG.toCharArray(), toClient); //Send input back to the client
            }

            // close I/O streams and sockets
            fromClient.close();
            toClient.close();
            clientSocket.close();
        } catch (IOException e) {
            System.out.println(e);
        }
        // control message for when a client closes/exits their socket
        finally {
            System.out.println("Client " + clientNumber + " exited");
        }
    }

    public void start() {
        if (currentThread == null) {

            // creating a new thread for new client
            currentThread = new Thread(this);
            currentThread.start();
        }
    }

    public boolean checkState(String state) {
        return state.contains("quit");
    }

    public void sendMSG(char clientMSG[], DataOutputStream outStream) {
        String charByChar = "";

        for (char currChar : clientMSG) {
            String serverMSG = "From Server to Client " + clientNumber + " " + charByChar + "\n";
            charByChar += currChar + "\n";
        }

        String serverMSG = "From Server to Client " + clientNumber + " Here's your data: \n" + charByChar;

        try {
            outStream.writeUTF(serverMSG + "\nChars in sequence so far: " + stateMachine);
            outStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
