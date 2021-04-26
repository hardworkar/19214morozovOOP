import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Baker extends Thread {

    // shared resources
    private final OrderQueue orderQueue;
    private final PizzaStorage pizzaStorage;

    // флаг окончания смены, выставляемый Manager
    private final AtomicBoolean dayEnded;

    // количество пекарей для учета при завершении работы
    private final AtomicInteger bakers;

    // время готовки пиццы стажером (= нулевой опыт)
    private final static int internCookingTime = 30;

    // время готовки одной пиццы поваром
    private final int cookingTime;


    public Baker(int experience, OrderQueue orderQueue, PizzaStorage pizzaStorage, AtomicBoolean dayEnded, AtomicInteger bakers){
        if(experience < 0){
            throw new IllegalArgumentException("Experience cannot be a negative value!");
        }

        // bakers properties
        // время готовки обратно пропорционально опыту пекаря
        this.cookingTime = internCookingTime / (experience + 1);

        // shared properties
        this.orderQueue = orderQueue;
        this.pizzaStorage = pizzaStorage;
        this.dayEnded = dayEnded;
        this.bakers = bakers;
    }

    // default behaviour of a baker
    public void run(){
        while (!dayEnded.get()) {

            int currentOrder;

            // wait for an order from orderQueue
            synchronized (orderQueue) {
                while (orderQueue.isEmpty()) {
                    try {
                        orderQueue.wait();
                        if(dayEnded.get()){
                            /* сюда мы можем попасть только когда Manager выставит флаг конца смены */
                            bakers.decrementAndGet();
                            return;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                currentOrder = orderQueue.pop();
                orderQueue.notifyAll();
            }

            // process it
            try {
                sleep(cookingTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // wait for a space in pizzaStorage
            synchronized (pizzaStorage) {
                while (pizzaStorage.isFull()) {
                    try {
                        pizzaStorage.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // place pizza in storage and notify about completion
                pizzaStorage.addLast(currentOrder);

                System.out.println("Order #" + currentOrder + " placed in storage\n");
                pizzaStorage.notifyAll();
            }
        }
        // завершаем работу - декрементируем счетчик живых пекарей
        bakers.decrementAndGet();
    }
}
