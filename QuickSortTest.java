
import java.util.concurrent.ForkJoinPool;


public class QuickSortTest {
    public static void main(String[] args){
        long st0, st1, stotal = 0;
        int arraySize = 10000;

    	int list[] = new int[arraySize];

        for (int i = 0; i < list.length; i++) {
			list[i] = (int)(Math.random() * 100);
		}

        //serial quicksort
		st0 = System.nanoTime();
		System.out.println("\nBegin Quicksort...");
        ForkJoinPool pool = ForkJoinPool.commonPool();
        pool.invoke(new QuickSort(0, arraySize - 1, list));
		st1 = System.nanoTime();
		
        stotal = st1 - st0;
		System.out.println("\nTime taken for parallel quicksort: " + stotal);
    }
}

