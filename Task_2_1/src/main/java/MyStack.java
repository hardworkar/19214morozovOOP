import java.util.Iterator;

public class MyStack<T> implements Iterable<T>{
    private Object[] data = new Object[10];
    private int cnt = 0;
    private int capacity = 10;

    private boolean isEmpty(){
        return cnt == 0;
    }

    /**
     * Pushes element on the top of stack. Reallocate memory for data (exponential growth).
     * @param val element of type t to push on the top of stack.
     */
    public void push(T val){
        if(cnt == capacity){
            capacity *= 2;
            Object[] newData = new Object[capacity];
            System.arraycopy(data, 0, newData, 0, cnt);
            data = newData;
        }
        data[cnt++] = val;
    }

    /**
     * @return extracting top element of the stack
     * if an empty stack was used, then a <tt>IllegalArgumentException</tt> will be thrown.
     */
    public T pop(){
        if(isEmpty()){
            throw new IllegalArgumentException("Stack is empty");
        }
        return (T)data[--cnt];
    }
    public int count(){
        return cnt;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private int currentIndex = 0;
            public boolean hasNext() {
                return currentIndex < cnt;
            }
            public T next() {
                return (T)data[currentIndex++];
            }
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}