public class Heap {
    int[] array;
    int heapSize;
    Heap(int[] arr){
        heapSize = 0;
        array = new int[arr.length];
        for (int j : arr) {
            array[heapSize++] = j;
            siftUp(heapSize - 1);
        }
    }
    private void siftUp(int idx){
        if(array[idx] < array[(idx-1) / 2]){
            int c = array[idx];
            array[idx] = array[(idx-1) / 2];
            array[(idx-1) / 2] = c;
            siftUp((idx-1) / 2);
        }
    }
    private void siftDown(int idx){
        if(2 * idx + 1 >= heapSize)
            return;
        int left = 2 * idx + 1;
        int right = 2 * idx + 2;
        int j = left;
        if(right < heapSize && array[right] < array[left])
            j = right;
        if(array[idx] <= array[j])
            return;
        int c = array[idx];
        array[idx] = array[j];
        array[j] = c;
        siftDown(j);
    }
    public int extractMin(){
        int min = array[0];
        array[0] = array[heapSize-1];
        heapSize--;
        siftDown(0);
        return min;
    }
    public int[] sort(){
        int[] res = new int[heapSize];
        int i = 0;
        while(heapSize > 0)
           res[i++] = extractMin();
        return res;
    }
}
