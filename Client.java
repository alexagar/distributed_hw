import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
  
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
    			
				Integer list[] = new Integer[10];
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

class QuickSortMutliThreading extends RecursiveTask<Integer> {
 
    int start, end;
    int[] arr;
 
    /**
     * Finding random pivoted and partition
     * array on a pivot.
     * There are many different
     * partitioning algorithms.
     * @param start
     * @param end
     * @param arr
     * @return
     */
    private int partition(int start, int end,
                        int[] arr)
    {
 
        int i = start, j = end;
 
        // Decide random pivot
        int pivoted = new Random()
                         .nextInt(j - i)
                     + i;
 
        // Swap the pivoted with end
        // element of array;
        int t = arr[j];
        arr[j] = arr[pivoted];
        arr[pivoted] = t;
        j--;
 
        // Start partitioning
        while (i <= j) {
 
            if (arr[i] <= arr[end]) {
                i++;
                continue;
            }
 
            if (arr[j] >= arr[end]) {
                j--;
                continue;
            }
 
            t = arr[j];
            arr[j] = arr[i];
            arr[i] = t;
            j--;
            i++;
        }
 
        // Swap pivoted to its
        // correct position
        t = arr[j + 1];
        arr[j + 1] = arr[end];
        arr[end] = t;
        return j + 1;
    }
 
    // Function to implement
    // QuickSort method
    public QuickSortMutliThreading(int start,
                                   int end,
                                   int[] arr)
    {
        this.arr = arr;
        this.start = start;
        this.end = end;
    }
 
    @Override
    protected Integer compute()
    {
        // Base case
        if (start >= end)
            return null;
 
        // Find partition
        int p = partition(start, end, arr);
 
        // Divide array
        QuickSortMutliThreading left
            = new QuickSortMutliThreading(start,
                                          p - 1,
                                          arr);
 
        QuickSortMutliThreading right
            = new QuickSortMutliThreading(p + 1,
                                          end,
                                          arr);
 
        // Left subproblem as separate thread
        left.fork();
        right.compute();
 
        // Wait untill left thread complete
        left.join();
 
        // We don't want anything as return
        return null;
    }