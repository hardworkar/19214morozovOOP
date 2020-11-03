import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class PriorityQueueTest {

    @Test
    public void insert_extract() {
        PriorityQueue<Integer, String> myFullQueue = new PriorityQueue<>();
        assertNull(myFullQueue.extractMax());
        myFullQueue.insert(700, "cow");
        myFullQueue.insert(350, "boo");
        myFullQueue.insert(200, "moo");
        myFullQueue.insert(400, "koo");
        myFullQueue.insert(100, "soo");
        myFullQueue.insert(700, "foo");

        ArrayList<String> okStrArray = new ArrayList<>(Arrays.asList("cow","foo","koo","boo","moo","soo"));
        int i = 0;
        String max = myFullQueue.extractMax();

        while(max != null){
            System.out.println(max);
            assertEquals(max, okStrArray.get(i++));
            max = myFullQueue.extractMax();
        }
        assertNull(myFullQueue.extractMax());
        for(String entry : myFullQueue){
            System.out.println(entry);
        }
    }

    @Test
    public void stream() {
        PriorityQueue<Integer, String> myFullQueue = new PriorityQueue<>();
        myFullQueue.insert(700, "cow");
        myFullQueue.insert(350, "boo");
        myFullQueue.insert(200, "moo");
        myFullQueue.insert(400, "koo");
        myFullQueue.insert(100, "soo");
        myFullQueue.insert(700, "foo");
        myFullQueue.parallelStream().map(String::toUpperCase).forEach(System.out::println);
    }
}