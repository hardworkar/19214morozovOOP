import java.util.Formattable;
import java.util.Iterator;
import java.util.function.Predicate;

public class NewThread<T> extends Thread {

    final private Iterator<T> iterator;
    final private Predicate<T> predicate;
    private final Task_2_1_1.FoundFlag found;

    NewThread(Iterator<T> iterator, Predicate<T> predicate, Task_2_1_1.FoundFlag found){
        this.iterator = iterator;
        this.predicate = predicate;
        this.found = found;
        start();
    }

    public void run(){
        while(iterator.hasNext()){

            T obj = iterator.next();

            // примитив синхронизации
            if(found.get()){
                break;
            }

            if(predicate.test(obj)){
                System.out.println("Found: " + obj);
                found.set();
                break;
            }
        }
    }
}
