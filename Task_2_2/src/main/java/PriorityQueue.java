import java.util.*;

import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


public class PriorityQueue<K extends Comparable<K>, V> implements Iterable<V> {
    private Object[] keys = new Object[8];
    private Object[] values = new Object[8];
    private int capacity = 8, heapSize = 0;
    private void swap(int idx1, int idx2){
        K tmpKey = (K)keys[idx1];
        V tmpValue = (V)values[idx1];
        keys[idx1] = keys[idx2];
        values[idx1] = values[idx2];
        keys[idx2] = tmpKey;
        values[idx2] = tmpValue;
    }
    private void siftUp(int idx) {
        if(((K)keys[idx]).compareTo((K)keys[(idx-1) / 2]) > 0){
            swap(idx, (idx-1) / 2);
            siftUp((idx-1) / 2);
        }
    }
    private void siftDown(int idx) {
        if(2 * idx + 1 >= heapSize) {
            return;
        }
        int left = 2 * idx + 1;
        int right = 2 * idx + 2;
        int j = left;
        if(right < heapSize && ((K)keys[right]).compareTo((K)keys[left]) > 0) {
            j = right;
        }
        if(((K)keys[idx]).compareTo((K)keys[j]) >= 0) {
            return;
        }
        swap(idx, j);
        siftDown(j);
    }

    /**
     * finds and deletes pair with max key from queue
     * @return returns value with max key or null if queue is empty
     */
    public V extractMax() {
        if(heapSize == 0){
            return null;
        }
        V max = (V)values[0];
        swap(0, --heapSize);
        siftDown(0);
        return max;
    }


    /**
     * inserts pair(key,val) into queue
     * @param key must be comparable
     * @param val can be any type
     */
    public void insert(K key, V val){
        if(heapSize == capacity){
            capacity *= 2;
            keys = new Object[capacity];
            values = new Object[capacity];
        }
        keys[heapSize] = key;
        values[heapSize++] = val;
        siftUp(heapSize-1);
    }




    public class PriorityQueueSpliterator implements Spliterator<V>{
        int left, right;
        public PriorityQueueSpliterator() {
            int left = 0, right = heapSize - 1;
        }
        public PriorityQueueSpliterator(int f, int l) {
            left = f;
            right = l;
        }
        @Override
        public boolean tryAdvance(Consumer<? super V> action) {
            if (left <= heapSize - 1) {
                action.accept((V)values[left]);
                left++;
                return true;
            }
            return false;
        }

        @Override
        public Spliterator<V> trySplit() {
            int half = (right - left)/2;
            if (half <= 1) {
                // Not enough data to split
                return null;
            }
            int f = left;
            int l = left + half;
            left = left + half + 1;
            return new PriorityQueueSpliterator(f, l);
        }

        @Override
        public long estimateSize() {
            return right - left + 1;
        }

        @Override
        public int characteristics() {
            return SIZED | SUBSIZED | ORDERED;
        }
    }


    @Override
    public Iterator<V> iterator() {
        return new Iterator<>() {
            private int currentIndex = 0;
            public boolean hasNext() {
                return currentIndex < heapSize;
            }
            public V next() {
                return (V)values[currentIndex++];
            }
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    Stream<V> stream() {
        return StreamSupport.stream(new PriorityQueueSpliterator(), false);
    }

    Stream<V> parallelStream() {
        return StreamSupport.stream(new PriorityQueueSpliterator(), true);
    }

}
