import java.util.Iterator;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Stream;

public class Priority_queue<K extends Comparable<K>, V> implements Iterable<SortedMap.Entry<K,V>>/*, Stream<SortedMap.Entry<K,V>>*/ {
    private final SortedMap<K,V> queue = new TreeMap<K,V>();

    /**
     * inserts pair(key,val) into queue
     * @param key must be comparable
     * @param val can be any type
     */
    public void insert(K key, V val){
        queue.put(key, val);
    }

    /**
     * finds and deletes pair with max key from queue
     * @return returns Map.Entry with max key
     */
    public SortedMap.Entry<K,V> extractMax(){
        Map.Entry<K,V> result = queue
                .entrySet()
                .stream()
                .max(SortedMap.Entry.comparingByKey())
                .orElse(null);
        if(result != null)
            queue.remove(result.getKey());
        return result;
    }
    @Override
    public Iterator<SortedMap.Entry<K,V>> iterator() {
        return queue.entrySet().iterator();
    }
}
