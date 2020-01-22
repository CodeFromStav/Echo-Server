import java.net.*;
import java.io.*;

public class EchoServer {
  public static void main(String[] args) throws Exception {
    try{
      ServerSocket server=new ServerSocket(8080);
      int counter=0;
      System.out.println("Server Started ....");
      while(true){
        counter++;
        Socket serverClient=server.accept();  //server accept the client connection request
        System.out.println(" >> " + "Client No:" + counter + " started!");
        EchoThread et = new EchoThread(serverClient,counter); //send  the request to a separate thread
        et.start();
      }
    } catch(Exception e){
      System.out.println(e);
    }
  }
}
