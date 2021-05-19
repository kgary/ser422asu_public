package edu.asupoly.ser422.grocery;

class Pair<K, V> {
    public final K key;
    public final V value;
 
    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}