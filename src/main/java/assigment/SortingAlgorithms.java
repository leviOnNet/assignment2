package assigment;

import java.util.Queue;
import java.util.LinkedList;




public class SortingAlgorithms {



    public class radixSort {

        public void radixSort(int[] data) {

            final int radix = 10; // base 10

            @SuppressWarnings("unchecked")
            Queue<Integer>[] buckets = new Queue[radix];

            // Initialize all queues
            for (int i = 0; i < radix; i++) {
                buckets[i] = new LinkedList<>();
            }

            // Find max number (to know number of digits)
            int max = data[0];
            for (int i = 1; i < data.length; i++) {
                if (data[i] > max) {
                    max = data[i];
                }
            }

            // Do counting per digit
            int factor = 1;

            while (max / factor > 0) {

                // Place into buckets
                for (int i = 0; i < data.length; i++) {
                    int digit = (data[i] / factor) % radix;
                    buckets[digit].add(data[i]);
                }

                // Collect from buckets
                int k = 0;
                for (int i = 0; i < radix; i++) {
                    while (!buckets[i].isEmpty()) {
                        data[k++] = buckets[i].poll();
                    }
                }

                factor *= radix;
            }
        }
    }


//count occurances of each number in data[]
// store in count array indexed with numbers in data[];


    public static void countingSort(int[] data) {
        int n = data.length;
        if (n <= 1) return;
//find the max value
//find the ma value 
// where last index in count[] is equal to largest number in data[]
        int max = data[0];
        for (int i = 0; i < n; i++) {
            if (data[i] > max)
                max = data[i];
        }
//count ++ mean  value v was seen more thnonce 
//count the occurances of eachnumber in data[]
//store occurances in count[] indexed wiht numbers in data[]
        int[] count = new int[max + 1];
        for (int i = 0; i < n; i++) {
            count[data[i]]++;   // if value is seen more than once

        }

//
        for (int i = 1; i < count.length; i++) {
            count[i] = count[i - 1] + count[i];

        }


//phase 3 
        int[] temp = new int[n];
        for (int i = n - 1; i >= 0; i--) {
            temp[count[data[i] - 1]] = data[i];
            count[data[i]]--;
        }


    }
}








