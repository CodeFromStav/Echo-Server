import java.net.*;
import java.io.*;
public class Client {
  public static void main(String[] args) throws Exception {
  try{
    Socket clientSock=new Socket("127.0.0.1", 8080);
    DataInputStream inStream=new DataInputStream(clientSock.getInputStream());
    DataOutputStream outStream=new DataOutputStream(clientSock.getOutputStream());
    BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
    String clientMessage="",serverMessage="";
    while(!clientMessage.equals("bye")){
      System.out.println("Enter text :");
      clientMessage=br.readLine();
      outStream.writeUTF(clientMessage);
      outStream.flush();
      serverMessage=inStream.readUTF();
      System.out.println(serverMessage);
    }
    outStream.close();
    outStream.close();
    br.close();
  }catch(Exception e){
    System.out.println(e);
  }
  }
}
