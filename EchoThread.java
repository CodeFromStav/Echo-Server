import java.net.*;
import java.io.*;

public class EchoThread implements Runnable {
  Thread currThread;
  Socket clientSock;
  int clientNu;
  int squre;
  String stateMachine = "";

  EchoThread(Socket inSocket,int counter){
    clientSock = inSocket;
    clientNu = counter;
  }

  public void run() {
    try {
      DataInputStream inStream = new DataInputStream(clientSock.getInputStream()); //instream takes care of inputs
      DataOutputStream outStream = new DataOutputStream(clientSock.getOutputStream()); //instream takes care of output to client
      System.out.println("Streams created");
      String clientMSG = "", serverMSG = "";
      while(!checkState(stateMachine)) {
        clientMSG = inStream.readUTF().replaceAll("[^a-zA-Z ]", ""); //read client messages
        System.out.println("Data received");
        stateMachine += clientMSG.toLowerCase(); //Add input to the state machine
        sendMSG(clientMSG.toCharArray(), outStream); //Send input back to the client
      }
      inStream.close(); //close streams and sockets
      outStream.close();
      clientSock.close();
    } catch(IOException e) {
      System.out.println(e);
    } finally{
      System.out.println("Client " + clientNu + " exited");
    }
  }

  public void start() {
      if (currThread == null) {
        currThread = new Thread(this);
        currThread.start();
      }
  }

  public boolean checkState(String state) {
    return state.contains("quit");
  }

  public void sendMSG(char clientMSG[], DataOutputStream outStream) {
    String charByChar = "";
    for (char currChar: clientMSG) {
      String serverMSG = "From Server to Client " + clientNu + " " + charByChar + "\n";
      charByChar +=  currChar + "\n";
    }
    String serverMSG="From Server to Client " + clientNu + " Here's your data: \n" + charByChar;
    try {
      outStream.writeUTF(serverMSG + "\nChars in sequence so far: " + stateMachine);
      outStream.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
