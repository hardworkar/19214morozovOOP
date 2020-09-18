import java.util.Arrays;

public class Task_1_1 {
    public static void main(String[] args){
        int[] array = new int[] {10,2,1,3,0};
        System.out.println(Arrays.toString(sort(array)));
    }
    /*
     * simple sort function, returns sorted array
     * */
    public static int[] sort(int[] arr){
        Heap heap = new Heap(arr);
        return heap.sort();
    }
}