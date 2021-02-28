import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Predicate;

public class Task_2_1_1 {
    public static void main(String[] args){

        // test reading (1_000_000 nums)
        ArrayList<Integer> myNumbers = new ArrayList<>();
        File file = new File("primeNumbersExceptOne.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String text;
            while ((text = reader.readLine()) != null) {
                myNumbers.add(Integer.parseInt(text));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // predicate def
        Predicate<Integer> isNotPrime = (Integer x) -> {
            if(x <= 1){
                return true;
            }
            for(int i = 2 ; i*i <= x ; i++){
                if(x % i == 0)
                    return true;
            }
            return false;
        };

        // sequence version
        System.out.println("### Seq version");
        long start = System.nanoTime();
        boolean answer = false;
        for (Integer myNumber : myNumbers) {
            if (isNotPrime.test(myNumber)) {
                System.out.println("Found: " + myNumber);
                answer = true;
                break;
            }
        }
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Duration: " + duration);
        System.out.println("Answer: " + answer);


        // 1 subtask - raw threads
        System.out.println("\n### Raw threads version");
        start = System.nanoTime();
        Spawner<Integer> spawner = new Spawner<>();
        answer = spawner.spawn(myNumbers, isNotPrime, 6);
        duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Duration: " + duration);
        System.out.println("Answer: " + answer);

        // 2 subtask - ParallelStream
        System.out.println("\n### ParallelStream version");
        start = System.nanoTime();
        answer = myNumbers.parallelStream().anyMatch(isNotPrime);
        duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Duration: " + duration);
        System.out.println("Answer: " + answer);

    }

    private static class Sparser<T>{
        /**
         * строит на основе mainIterator новый итератор, выбирая каждый N-ый элемент
         */
        public Iterator<T> createSparseIterator(@NotNull Iterator<T> mainIterator, int N) {
            ArrayList<T> list = new ArrayList<>();
            int i = 0;
            while(mainIterator.hasNext()){
                if(i % N == 0){
                    list.add(mainIterator.next());
                }
                else{
                    mainIterator.next();
                }
                i++;
            }
            return list.iterator();
        }
    }

    private static class Spawner <T>{
        /**
         * параллельно обрабатывает коллекцию
         * @param nThreads количество потоков <= кол-во эл-ов в коллекции
         * @return есть ли элемент, на котором выполняется predicate
         */
        public boolean spawn(@NotNull ArrayList<T> collection, @NotNull Predicate<T> predicate, int nThreads) {

            // array for storing threads for further joining
            ArrayList<NewThread<T>> threads = new ArrayList<>();

            // flag for thread synchronization and answer
            FoundFlag found = new FoundFlag();

            // upper-bound threads with number of elements
            nThreads = Math.min(nThreads, collection.size());

            Sparser<T> mySparser = new Sparser<>();
            for(int i = 0 ; i < nThreads ; i++){
                // для каждого потока делаем свой итератор
                Iterator<T> iterator = collection.iterator();
                // смещая его на номер потока
                for(int j = 0 ; j < i ; j++){
                    if(iterator.hasNext()) {
                        iterator.next();
                    }
                    else{
                        throw new IllegalStateException();
                    }
                }
                // сохраняем потоки, чтобы в дальнейшем дождаться их в main-потоке
                threads.add(new NewThread<T>(mySparser.createSparseIterator(iterator, nThreads), predicate, found));
            }
            // ждем завершения всех потоков
            for(int i = 0 ; i < nThreads ; i++){
                try {
                    threads.get(i).join();
                }
                catch (InterruptedException e){
                    System.out.println("Main thread has been interrupted");
                }
            }
            return found.get();
        }
    }

    // флаговая переменная с синхронизованным доступом
    public static class FoundFlag{
        private boolean found = false;
        
        /**
         * устанавливает флаг
         */
        public synchronized void set(){
            found = true;
        }

        /**
         * проверяет флаг
         */
        public synchronized boolean get(){
            return found;
        }
    }
}
