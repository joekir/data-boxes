package com.joek.databoxes;

public interface DataBox<K, V> {
    public K getKey();
    public V getValue();
}
