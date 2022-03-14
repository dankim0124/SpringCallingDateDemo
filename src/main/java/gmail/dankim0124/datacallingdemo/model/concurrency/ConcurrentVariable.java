package gmail.dankim0124.datacallingdemo.model.concurrency;


import gmail.dankim0124.datacallingdemo.model.TickRes;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentVariable<K,V> {
    private ConcurrentHashMap concurrentMap;

    public ConcurrentVariable(int initialCapacity, float loadFactor, int concurrencyLevel){
        concurrentMap = new ConcurrentHashMap(initialCapacity,loadFactor,concurrencyLevel);
    }

    public ConcurrentVariable(int initialCapacity, float loadFactor ){
        concurrentMap = new ConcurrentHashMap(initialCapacity,loadFactor);
    }

    public ConcurrentVariable(int initialCapacity ){
        concurrentMap = new ConcurrentHashMap(initialCapacity);
    }

    public ConcurrentVariable( ){
        concurrentMap = new ConcurrentHashMap();
    }

    public void addAll(Map<K,V> adder){
        concurrentMap.putAll(adder);
    }

    public Collection getListOfValue(){
        List<TickRes> list = new ArrayList<>(concurrentMap.values()) ;
        return concurrentMap.values();
    }

    public int size(){
        return concurrentMap.size();
    }

    public Enumeration keys(){
        return concurrentMap.keys();
    }



}
