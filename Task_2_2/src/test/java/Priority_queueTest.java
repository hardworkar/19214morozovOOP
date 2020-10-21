import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import static org.junit.Assert.*;

public class Priority_queueTest {

    @Test
    public void insert_extract() {
        Priority_queue<Integer, String> myFullQueue = new Priority_queue<>();
        myFullQueue.insert(700, "cow");
        myFullQueue.insert(350, "boo");
        myFullQueue.insert(200, "moo");
        myFullQueue.insert(400, "koo");
        myFullQueue.insert(100, "soo");
        myFullQueue.insert(800, "foo");

        ArrayList<Integer> okIntArray = new ArrayList<>(Arrays.asList(800, 700, 400, 350, 200, 100));
        ArrayList<String> okStrArray = new ArrayList<>(Arrays.asList("foo","cow","koo","boo","moo","soo"));
        int i = 0;
        Map.Entry<Integer,String> max = myFullQueue.extractMax();
        while(max != null){
            assertEquals(max.getKey(), okIntArray.get(i));
            assertEquals(max.getValue(), okStrArray.get(i++));
            max = myFullQueue.extractMax();
        }

        for(Map.Entry<Integer, String> entry : myFullQueue){
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }
}