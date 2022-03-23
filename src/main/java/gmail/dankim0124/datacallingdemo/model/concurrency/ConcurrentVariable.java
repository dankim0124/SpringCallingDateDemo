package gmail.dankim0124.datacallingdemo.model.concurrency;

import lombok.Data;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class ConcurrentVariable<K, V> {
    private ConcurrentHashMap concurrentMap;

    public ConcurrentVariable(int initialCapacity, float loadFactor, int concurrencyLevel) {
        concurrentMap = new ConcurrentHashMap(initialCapacity, loadFactor, concurrencyLevel);
    }

    public ConcurrentVariable(int initialCapacity, float loadFactor) {
        concurrentMap = new ConcurrentHashMap(initialCapacity, loadFactor);
    }

    public ConcurrentVariable(int initialCapacity) {
        concurrentMap = new ConcurrentHashMap(initialCapacity);
    }

    public ConcurrentVariable() {
        concurrentMap = new ConcurrentHashMap();
    }

    public void addAll(Map<K, V> adder) {
        concurrentMap.putAll(adder);
    }

    public Collection Values() {
        return concurrentMap.values();
    }

    public void clear() {
        concurrentMap.clear();
    }

    public int size() {
        return concurrentMap.size();
    }

    public Enumeration keys() {
        return concurrentMap.keys();
    }

    public synchronized List atomicValueListAndClear() {
        Collection collection = concurrentMap.values();
        ArrayList valueList = new ArrayList(collection);
        concurrentMap.clear();
        return valueList;
    }


}
