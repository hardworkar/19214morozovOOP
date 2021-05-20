public class OrderQueue extends ThreadSafeDeque<Integer>{
    public OrderQueue(int dequeMaxSize) {
        super(dequeMaxSize);
    }
}
