import java.net.Socket;
import java.net.ServerSocket;

class EchoServer
{
    

    /* @param args
    */
    public static void main (String[] args) throws Exception
    {

        String data;

        int port = 8080;

        //create server socket object
        ServerSocket serverSocket = new ServerSocket( port ); 
        System.err.println( "Server started on port " + port );

        while( true )
        {
            //accept incoming request to socket using hostname,port,localport
            Socket clientSocket = serverSocket.accept();
            System.err.println( "Accepted connection from client" );

            //open I/O streams
            Input in = new Input ( clientSocket );
            Output out = new Output ( clientSocket );

            //wait for data and read until connection stops
            //readLine() blocks until the server recieved a new line from client
            while ( ( data = in.readLine() ) != null )
            {
                System.out.println( data );
            }

            System.err.println( "Closing connection with client..." );
            out.close();
            in.close();
            clientSocket.close();
            System.err.println( "Closed.");
        }

    }

}

