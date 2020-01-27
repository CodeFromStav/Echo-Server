import java.net.*;
import java.io.*;

public class Client {

    public static void main(String[] args) throws Exception {

        try {
            Socket clientSock = new Socket("127.0.0.1", 8080);
            DataInputStream inStream = new DataInputStream(clientSock.getInputStream());
            DataOutputStream outStream = new DataOutputStream(clientSock.getOutputStream());
            BufferedReader buffRead = new BufferedReader(new InputStreamReader(System.in));
            String clientMessage = "", serverMessage = ""; //add text here?

            //Examples of shutdown sequences include: 'quit', 'q123u$#i66t', 'q0u0i0t', 'q u i t'
            while (!clientMessage.equals("bye")) {
                System.out.println("Enter text :");
                clientMessage = buffRead.readLine();
                outStream.writeUTF(clientMessage);
                outStream.flush();
                serverMessage = inStream.readUTF();
                System.out.println(serverMessage);
            }
            //close I/O stream, bufferReader, and clientSocket
            outStream.close();
            outStream.close();
            buffRead.close();
            clientSock.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
