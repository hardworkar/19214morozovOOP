import org.junit.Test;
import org.junit.Assert;

public class Task_1_1Test {

    @Test
    public void EasyArrays() {
        int[] arr = new int[]{1, 2, 3, 4, 5};
        int[] sortedArr = Task_1_1.sort(arr);
        Assert.assertArrayEquals(arr, sortedArr);
        Assert.assertArrayEquals(new int[]{777}, Task_1_1.sort(new int[]{777}));
        Assert.assertArrayEquals(new int[]{}, Task_1_1.sort(new int[]{}));
        Assert.assertArrayEquals(new int[]{33, 44}, Task_1_1.sort(new int[]{44, 33}));
        Assert.assertArrayEquals(new int[]{0, 0, 0, 0, 0}, Task_1_1.sort(new int[]{0, 0, 0, 0, 0}));
    }
}