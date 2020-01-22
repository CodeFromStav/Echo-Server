import java.net.*;
import java.io.*;
public class EchoThread implements Runnable {
  Thread currThread;
  Socket clientSock;
  int clientNu;
  int squre;
  EchoThread(Socket inSocket,int counter){
    clientSock = inSocket;
    clientNu=counter;
  }
  public void run(){
    try{
      DataInputStream inStream = new DataInputStream(clientSock.getInputStream());
      DataOutputStream outStream = new DataOutputStream(clientSock.getOutputStream());
      String clientMSG="", serverMSG="";
      while(!clientMSG.equals("bye")){
        clientMSG=inStream.readUTF();
        System.out.println("From Client-" +clientNu+ ": Number is :"+clientMSG);
        squre = Integer.parseInt(clientMSG) * Integer.parseInt(clientMSG);
        serverMSG="From Server to Client-" + clientNu + " Square of " + clientMSG + " is " +squre;
        outStream.writeUTF(serverMSG);
        outStream.flush();
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
}
