import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Task_2_1_2 {
    public static void main(String[] args){

        PizzaStorage pizzaStorage = new PizzaStorage(10);
        OrderQueue orderQueue = new OrderQueue(5);

        ArrayList<Courier> couriers = new ArrayList<>();
        ArrayList<Baker> bakers = new ArrayList<>();

        // флаговая переменная, которую выставит Manager, когда добавит в очередь все заказы
        AtomicBoolean dayEnded = new AtomicBoolean(false);

        // количество заказов в смене
        // эту переменную будут декрементировать курьеры, подтверждая доставку
        AtomicInteger numberOfOrders = new AtomicInteger(1000);

        // количество активных пекарей. Будет декрементироваться завершающими исполнение пекарями
        AtomicInteger numberOfBakers = new AtomicInteger(200);

        // аналогично для курьеров
        AtomicInteger numberOfCouriers = new AtomicInteger(3000);

        for(int i = 1 ; i <= numberOfCouriers.get() ; i++)
            couriers.add(new Courier(i, pizzaStorage, dayEnded, numberOfOrders, numberOfCouriers));

        for(int i = 1 ; i <= numberOfBakers.get() ; i++)
            bakers.add(new Baker(i, orderQueue, pizzaStorage, dayEnded, numberOfBakers));

        Manager manager = new Manager(pizzaStorage, orderQueue, bakers, couriers, dayEnded);
        manager.simulateDay(numberOfOrders, numberOfBakers, numberOfCouriers);

    }
}
