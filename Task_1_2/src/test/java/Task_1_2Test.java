import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class Task_1_2Test {

    @Test
    public void searchString() throws IOException {
        String str1 = "texttexttext";
        ArrayList<Long> answer1 = new ArrayList<>();
        long[] _answer1 = {0, 4, 8};
        for (long i : _answer1) {
            answer1.add(i);
        }
        InputStream in = new ByteArrayInputStream(str1.getBytes());
        Assert.assertEquals(Task_1_2.searchString(in, "text"), answer1);
        in.close();

        String str2 = "тттттттттт";
        ArrayList<Long> answer2 = new ArrayList<>();
        long[] _answer2 = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        for (long i : _answer2) {
            answer2.add(i);
        }
        in = new ByteArrayInputStream(str2.getBytes());
        Assert.assertEquals(Task_1_2.searchString(in, "т"), answer2);
        in.close();

        String str3 = "異體字體字異";
        ArrayList<Long> answer3 = new ArrayList<>();
        long[] _answer3 = {0, 5};
        for (long i : _answer3) {
            answer3.add(i);
        }
        in = new ByteArrayInputStream(str3.getBytes());
        Assert.assertEquals(Task_1_2.searchString(in, "異"), answer3);
        in.close();
    }
}