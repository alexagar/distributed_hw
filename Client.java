import java.io.*;
import java.net.*;

// Client class
public class Client {
    
    // driver code
    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException
    {
		long st0, st1, stotal = 0;
		long pt0, pt1, ptotal = 0;

		int arraySize = 10000;

    	int list[] = new int[arraySize];
		int list2[] = new int[arraySize];

		for (int i = 0; i < list.length; i++) {
			list[i] = (int)(Math.random() * 10000);
			list2[i] = list[i];
		}

		//serial quicksort
		if(arraySize == 10){
			System.out.println("Serial quick sort before: ");
			for (int i = 0; i < list.length; i++) {
				System.out.print(list2[i] + " ");
			}
		}

		st0 = System.nanoTime();
		quickSort(list2, 0, list2.length - 1);
		st1 = System.nanoTime();

		if(arraySize == 10){
			System.out.println("\n\nSerial quick sort after: ");
			for (int i = 0; i < list.length; i++) {
				System.out.print(list2[i] + " ");
			}
		}

		stotal = st1 - st0;
		System.out.println("\nTime taken for serial quicksort: " + stotal);

		// establish a connection by providing host and port number
		System.out.println("\n\nBeginning socket connection...");
        try (Socket socket = new Socket("10.78.141.142", 5555)) {
            
            // writing to server
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			
			if(arraySize == 10){
				System.out.println("\nParallel quick sort before: ");
				for (int i = 0; i < arraySize; i++) {
					System.out.print(list[i] + " ");
				}
			}

			pt0 = System.nanoTime();
			// Communication process (initial sends/receives
			System.out.println("\nSending array elements...");
			out.writeObject(list);// send array
			list = (int[]) in.readObject();
			pt1 = System.nanoTime();

			if(arraySize == 10){
				System.out.println("\nParallel quick sort after: ");
				for (int i = 0; i < arraySize; i++) {
					System.out.print(list[i] + " ");
				}
			}

			ptotal = pt1 - pt0;
			System.out.println("\nTime taken for parallel quicksort: " + ptotal);

			System.out.println("\n\nClosing socket and terminating program.");
			socket.close();
        }
    }

	static void swap(int[] arr, int i, int j)
	{
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}
 
	/* This function takes last element as pivot, places
	the pivot element at its correct position in sorted
	array, and places all smaller (smaller than pivot)
	to left of pivot and all greater elements to right
	of pivot */
	static int partition(int[] arr, int low, int high)
	{
		
		// pivot
		int pivot = arr[high];
		
		// Index of smaller element and
		// indicates the right position
		// of pivot found so far
		int i = (low - 1);
	
		for(int j = low; j <= high - 1; j++)
		{
			
			// If current element is smaller
			// than the pivot
			if (arr[j] < pivot)
			{
				
				// Increment index of
				// smaller element
				i++;
				swap(arr, i, j);
			}
		}
		swap(arr, i + 1, high);
		return (i + 1);
	}
	
	/* The main function that implements QuickSort
			arr[] --> Array to be sorted,
			low --> Starting index,
			high --> Ending index
	*/
	static void quickSort(int[] arr, int low, int high)
	{
		if (low < high)
		{
			
			// pi is partitioning index, arr[p]
			// is now at right place
			int pi = partition(arr, low, high);
	
			// Separately sort elements before
			// partition and after partition
			quickSort(arr, low, pi - 1);
			quickSort(arr, pi + 1, high);
		}
	}
}