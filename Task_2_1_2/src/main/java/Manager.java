import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

public class Manager {
    private final PizzaStorage pizzaStorage;
    private final OrderQueue orderQueue;
    private final ArrayList<Baker> bakers;
    private final ArrayList<Courier> couriers;
    private final AtomicBoolean dayEnded;
    public Manager(PizzaStorage pizzaStorage, OrderQueue orderQueue, ArrayList<Baker> bakers, ArrayList<Courier> couriers, AtomicBoolean dayEnded){
        this.pizzaStorage = pizzaStorage;
        this.orderQueue = orderQueue;
        this.bakers = bakers;
        this.couriers = couriers;
        this.dayEnded = dayEnded;
    }
    public void simulateDay(AtomicInteger numberOfOrders, AtomicInteger numberOfBakers, AtomicInteger numberOfCouriers) {
        // запускаем работников
        int localNumOfOrders = numberOfOrders.get();
        for(Courier courier : couriers){
            courier.start();
        }
        for(Baker baker : bakers){
            baker.start();
        }

        // ставим все заказы в очередь
        for(int i = 0 ; i < localNumOfOrders ; i++){
            synchronized(orderQueue){
                while(orderQueue.isFull()){
                    try {
                        orderQueue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                orderQueue.addLast(i);
                orderQueue.notifyAll();
            }
        }

        // ждем, пока все заказы доставятся - тогда говорим всем через флаг, что день закончился
        while(numberOfOrders.get() > 0);

        dayEnded.set(true);

        // аккуратно поднимаем и дожидаемся все потоки
        // ниже написано нечто не совсем адекватное, но я не смог придумать решение лучше
        // по сути, я поднимаю все треды, сидящие на мониторах shared-объектов

        while(numberOfBakers.get() > 0){
            synchronized (orderQueue){
                orderQueue.notify();
            }
            synchronized (pizzaStorage){
                pizzaStorage.notify();
            }
        }
        while(numberOfCouriers.get() > 0){
            synchronized (orderQueue){
                orderQueue.notify();
            }
            synchronized (pizzaStorage){
                pizzaStorage.notify();
            }
        }

        for(Baker baker : bakers){
            try{
                baker.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for(Courier courier : couriers){
            try {
                courier.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // симуляция завершилась
        System.out.println("Смена окончена!\n");
    }
}
