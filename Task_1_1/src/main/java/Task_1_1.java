import java.util.Arrays;

public class Task_1_1 {

    public static void main(String[] args){
        int[] array = new int[] {10,2,1,3,0};
        System.out.println(Arrays.toString(sort(array)));
    }
    // simple sort
    public static int[] sort(int[] arr){
        return Heap.sort(arr);
    }

    private static class Heap {
        private final int[] array;
        private int heapSize;
        Heap(int[] arr){
            heapSize = 0;
            array = new int[arr.length];
            for (int j : arr) {
                array[heapSize++] = j;
                siftUp(heapSize - 1);
            }
        }
        private void siftUp(int idx) {
            if(array[idx] < array[(idx-1) / 2]){
                int c = array[idx];
                array[idx] = array[(idx-1) / 2];
                array[(idx-1) / 2] = c;
                siftUp((idx-1) / 2);
            }
        }
        private void siftDown(int idx) {
            if(2 * idx + 1 >= heapSize) {
                return;
            }
            int left = 2 * idx + 1;
            int right = 2 * idx + 2;
            int j = left;
            if(right < heapSize && array[right] < array[left]) {
                j = right;
            }
            if(array[idx] <= array[j]) {
                return;
            }
            int c = array[idx];
            array[idx] = array[j];
            array[j] = c;
            siftDown(j);
        }
        private int extractMin() {
            int min = array[0];
            array[0] = array[heapSize-1];
            heapSize--;
            siftDown(0);
            return min;
        }
        private static int[] sort(int[] arr) {
            Heap heap = new Heap(arr);
            int[] res = new int[heap.heapSize];
            int i = 0;
            while(heap.heapSize > 0) {
                res[i++] = heap.extractMin();
            }
            return res;
        }
    }

}