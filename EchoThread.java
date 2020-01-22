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
    clientNu=counter;
  }
  public void run(){
    try{
      DataInputStream inStream = new DataInputStream(clientSock.getInputStream());
      DataOutputStream outStream = new DataOutputStream(clientSock.getOutputStream());
      String clientMSG="", serverMSG="";
      while(!checkState(stateMachine)){
        clientMSG=inStream.readUTF().replaceAll("[^a-zA-Z ]", "");
        stateMachine += clientMSG.toLowerCase();
        //System.out.println(clientMSG);
        //squre = Integer.parseInt(clientMSG) * Integer.parseInt(clientMSG);
        sendMSG(clientMSG.toCharArray(), outStream);
      }
      inStream.close();
      outStream.close();
      clientSock.close();
    }catch(IOException e){
      System.out.println(e);
    }finally{
      System.out.println("Client -" + clientNu + " exit!! ");
    }
  }
  public void start () {
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
      charByChar +=  currChar + "\n";
    }
    String serverMSG="From Server to Client-" + clientNu + " Here's your data baby girl: \n" + charByChar;
    try {
      outStream.writeUTF(serverMSG + "\nChars in sequence so far: " + stateMachine);
      outStream.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
