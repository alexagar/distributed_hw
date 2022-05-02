import java.io.*;
import java.net.*;

  
// Client class
public class Client {
    
    // driver code
    public static void main(String[] args) throws UnknownHostException, IOException
    {
    	
        // establish a connection by providing host and port
        // number
        try (Socket socket = new Socket("10.78.141.142", 5555)) {
            
            // writing to server
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
  
          	// Variables for message passing	
    			
				int list[] = new int[10];
				for (int i = 0; i < list.length; i++) {
					list[i] = (int)(Math.random() * 100);
				}
    			//long t0, t1, t, total = 0;

    			// Communication process (initial sends/receives
				System.out.println("Sending array elements...");
    			out.writeObject(list);// send array
    			
    			/*t0 = System.currentTimeMillis();
    			t1 = System.currentTimeMillis();

    			t = t1 - t0;
    			total += t;

    			t0 = System.currentTimeMillis();*/

    			System.out.println("Closing socket and terminating program.");
        		socket.close();
        }
    }
}