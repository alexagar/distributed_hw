import java.io.*;
import java.net.*;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.TimeUnit;
  
// Server class
class Server {
    public static void main(String[] args)
    {

        ServerSocket server = null;
  
        try {
  
            // server is listening on port 1234
            server = new ServerSocket(5555);
            System.out.println("listening on port 5555");
            server.setReuseAddress(true);

            // running infinite loop for getting
            // client request
            while (true) {
                // socket object to receive incoming client
                // requests
                Socket client = server.accept();
  
                // Displaying that new client is connected
                // to server
                System.out.println("New client connected: "
                                   + client.getInetAddress()
                                         .getHostAddress());
  
                // create a new thread object
                ClientHandler clientSock
                    = new ClientHandler(client);
  
                // This thread will handle the client
                // separately
                new Thread(clientSock).start();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (server != null) {
                try {
                    server.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
  
    
    // ClientHandler class
    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;
  
        // Constructor
        public ClientHandler(Socket socket)
        {
            this.clientSocket = socket;
        }
  
        public void run()
        {
        	
            PrintWriter out = null;
            BufferedReader in = null;
            try {
                  // get the outputstream of client
                out = new PrintWriter(
                    clientSocket.getOutputStream(), true);
  
                  // get the inputstream of client
                in = new BufferedReader(
                    new InputStreamReader(
                        clientSocket.getInputStream()));
                
                
                String fromServer; // messages sent to ServerRouter
                String fromClient; // messages received from ServerRouter     
        		String address ="message from the Server"; // destination IP (Client)
       			
       			// Communication process (initial sends/receives)
       			out.println(address);// initial send (IP of the destination Client)
       			fromClient = in.readLine();// initial receive from router (verification of connection)
       			System.out.println("ServerRouter: " + fromClient);
       			         
       			// Communication while loop
             	while ((fromClient = in.readLine()) != null) {
                   System.out.println("Client said: " + fromClient);
                   if (fromClient.equals("Bye.")) // exit statement
       					break;
       				fromServer = fromClient.toUpperCase(); // converting received message to upper case
       				System.out.println("Server said: " + fromServer);
                   out.println(fromServer); // sending the converted message back to the Client via ServerRouter
                }
                
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                        clientSocket.close();
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            
        }
    }
}