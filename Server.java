import java.io.*;
import java.net.*;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.TimeUnit;
  
// Server class
class Server {
    public static void main(String[] args) throws IOException, ClassNotFoundException
    {
        ServerSocket server = new ServerSocket(5555);
        System.out.println("listening on port 5555");
        Socket socket = server.accept();

        //get input
        InputStream inputStream = socket.getInputStream();
        // get the outputstream of client
        ObjectInputStream objectInput = new ObjectInputStream(inputStream);

        //read messages from socket
        Integer list[] = (Integer[]) objectInput.readObject();
        System.out.println("\nReceived [" + list.length + "] items");

        System.out.println("\nBefore: ");

        for(int i = 0; i < list.length; i++){
            System.out.print(list[i] + " ");
        }

        System.out.println("\n\nClosing socket...");
        server.close();
        socket.close();

    }
}