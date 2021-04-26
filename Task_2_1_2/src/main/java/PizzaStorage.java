public class PizzaStorage extends ThreadSafeDeque<Integer>{
    public PizzaStorage(int dequeMaxSize) {
        super(dequeMaxSize);
    }
}
