import java.io.*;
import java.net.*;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
  
// Server class
public class Server {
    public static void main(String[] args) throws IOException, ClassNotFoundException
    {

        ServerSocket server = new ServerSocket(5555);
        System.out.println("listening on port 5555");
        Socket socket = server.accept();

        //get input
        InputStream inputStream = socket.getInputStream();
        // get the outputstream of client
        ObjectInputStream objectInput = new ObjectInputStream(inputStream);

        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

        //read messages from socket
        int list[] = (int[]) objectInput.readObject();
        System.out.println("\nReceived [" + list.length + "] items");

        int n = list.length;

        System.out.println("\nBegin Quicksort...");
        ForkJoinPool pool = ForkJoinPool.commonPool();
        pool.invoke(new QuickSort(0, n - 1, list));

        out.writeObject(list);

        // get the outputstream of client
        ObjectOutputStream objectOutput = new ObjectOutputStream(socket.getOutputStream());

        

        System.out.println("\n\nClosing socket...");

        

        server.close();
        socket.close();

    }
}

class QuickSort extends RecursiveTask<Integer> {
 
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
    private int partition(int start, int end,int[] arr)
    {
        int i = start, j = end;
 
        // Decide random pivot
        int pivoted = new Random().nextInt(j - i) + i;
 
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
    public QuickSort(int start, int end, int[] arr)
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
        QuickSort left = new QuickSort(start, p - 1, arr);
 
        QuickSort right = new QuickSort(p + 1, end, arr);
 
        // Left subproblem as separate thread
        left.fork();
        right.compute();
 
        // Wait untill left thread complete
        left.join();
 
        // We don't want anything as return
        return null;
    }
}