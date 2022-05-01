import java.io.*;
import java.net.*;
import java.util.*;
import java.io.File;
import java.io.IOException;
  
// Client class
class Client {
    
    // driver code
    public static void main(String[] args) throws UnknownHostException, IOException
    {
    	
        // establish a connection by providing host and port
        // number
        try (Socket socket = new Socket("10.78.141.142", 5556)) {
            
            // writing to server
            PrintWriter out = new PrintWriter(
                socket.getOutputStream(), true);
  
            // reading from server
            BufferedReader in
                = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
            
				String file_send = "file.txt";
          // Variables for message passing	
    			File f = new File(file_send);
    			Reader reader = new FileReader(file_send); 
    			BufferedReader fromFile =  new BufferedReader(reader); // reader for the string file
    			String fromServer; // messages received from ServerRouter
    			String fromUser; // messages sent to ServerRouter
    			long t0, t1, t, total = 0;
    			
    			String address ="message from the client"; // destination IP (Server)
    			// Communication process (initial sends/receives
    			out.println(address);// initial send (IP of the destination Server)

    			fromServer = in.readLine();//initial receive from router (verification of connection)
    			System.out.println("ServerRouter: " + fromServer);
    			
    			InetAddress addr = InetAddress.getLocalHost();
    			String host = addr.getHostAddress();
    			out.println(host); // Client sends the IP of its machine as initial send
    			
    			t0 = System.currentTimeMillis();
       	
    			// Communication while loop
    			while ((fromServer = in.readLine()) != null) {
    				System.out.println("Server: " + fromServer);
    				t1 = System.currentTimeMillis();
    				
    				if (fromServer.equals("Bye.")) { // exit statement
    					break;
    				}
    			t = t1 - t0;
    			total += t;
    			System.out.println("Total Cycle Time: " + total + " File Size: " + f.length());;
    				
    				
    			fromUser = fromFile.readLine(); // reading strings from a file
            	if (fromUser != null) {
            		System.out.println("Client: " + fromUser);
            		out.println(fromUser); // sending the strings to the Server via ServerRouter
    				t0 = System.currentTimeMillis();
    				}
            	}
    			System.out.println("Done with stuff");
    			
    		
        }
    }
}