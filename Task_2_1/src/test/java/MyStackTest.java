import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class MyStackTest {

    @Test
    public void push() {
        MyStack<Integer> st = new MyStack<>();
        for(int i = 0 ; i < 3000 ; i++){
            st.push(i);
        }
        ArrayList<Integer> commonStackContents = new ArrayList<>(3000);
        for(Integer item : st){
            commonStackContents.add(item);
        }
        ArrayList<Integer> coolStackContents = new ArrayList<>(3000);
        for(int i = 0 ; i < 3000 ; i++){
            coolStackContents.add(i);
        }
        assertEquals(commonStackContents, coolStackContents);
    }

    @Test
    public void pop() {
        MyStack<Integer> st = new MyStack<>();
        for(int i = 0 ; i < 10 ; i++){
            st.push(i);
        }
        st.pop();
        st.pop();
        ArrayList<Integer> commonStackContents = new ArrayList<>(8);
        for(Integer item : st){
            commonStackContents.add(item);
        }
        ArrayList<Integer> coolStackContents = new ArrayList<>(8);
        for(int i = 0 ; i < 8 ; i++){
            coolStackContents.add(i);
        }
        assertEquals(commonStackContents, coolStackContents);
    }

    @Test
    public void count() {
        MyStack<Integer> st = new MyStack<>();
        assertEquals(st.count(), 0);
        st.push(145);
        assertEquals(st.count(), 1);
        st.push(145);
        assertEquals(st.count(), 2);
        st.pop();
        assertEquals(st.count(), 1);
        st.pop();
        assertEquals(st.count(), 0);
    }
}