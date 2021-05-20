import java.util.Stack;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Courier extends Thread {
    // deliver properties
    private final int bagMaxSize;
    private final Stack<Integer> bag = new Stack<>();

    // shared properties
    private final PizzaStorage pizzaStorage;
    private final AtomicBoolean dayEnded;
    private final AtomicInteger numberOfOrders;
    private final AtomicInteger couriers;

    public Courier(int bagMaxSize, PizzaStorage pizzaStorage, AtomicBoolean dayEnded, AtomicInteger numberOfOrders, AtomicInteger couriers){
        if(bagMaxSize <= 0){
            throw new IllegalArgumentException("You should probably get rid of this deliver, man");
        }
        this.bagMaxSize = bagMaxSize;

        this.pizzaStorage = pizzaStorage;
        this.dayEnded = dayEnded;
        this.numberOfOrders = numberOfOrders;
        this.couriers = couriers;
    }

    public void run() {
        while (!dayEnded.get()) {
            // wait for some pizzas in pizzaStorage
            synchronized (pizzaStorage) {
                while (pizzaStorage.isEmpty()) {
                    try {
                        pizzaStorage.wait();
                        if(dayEnded.get()){
                            /* сюда мы можем попасть только когда Manager выставит флаг конца смены */
                            couriers.decrementAndGet();
                            return;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                while (pizzaStorage.getQueueSize() > 0 && bag.size() < bagMaxSize) {
                    bag.push(pizzaStorage.pop());
                }
                pizzaStorage.notifyAll();
            }

            // deliver them and notify about completions
            while (!bag.isEmpty()) {
                try {
                    // "доставляем"
                    sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Delivered order #" + bag.pop() + "\n");
                numberOfOrders.decrementAndGet();
            }
        }
        // завершаем работу - декрементируем счетчик живых курьеров
        couriers.decrementAndGet();
    }
}
