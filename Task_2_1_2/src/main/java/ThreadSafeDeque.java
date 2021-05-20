import java.util.ArrayDeque;

public class ThreadSafeDeque<T> {
    private final ArrayDeque<T> deque = new ArrayDeque<>();
    private final int dequeMaxSize;
    public ThreadSafeDeque(int dequeMaxSize){
        if (dequeMaxSize < 0) {
            throw new IllegalArgumentException();
        }
        this.dequeMaxSize = dequeMaxSize;
    }
    public T pop() {
        synchronized (this) {
            if (deque.size() == 0) {
                throw new IllegalStateException();
            }
            return deque.pop();
        }
    }
    public void addLast(T value){
        synchronized (this) {
            if (deque.size() == dequeMaxSize) {
                throw new IllegalStateException();
            }
            deque.addLast(value);
        }
    }
    public int getQueueSize(){
        synchronized (this) {
            return deque.size();
        }
    }
    public boolean isFull(){
        synchronized (this) {
            return dequeMaxSize == deque.size();
        }
    }

    public boolean isEmpty() {
        synchronized (this) {
            return deque.isEmpty();
        }
    }
}
