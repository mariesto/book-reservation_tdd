package com.mariesto.book_reservation.service.cache;

public interface CacheProvider {

    void put(String cacheName, String key, Object value);

    Object get(String cacheName, String key);

    void remove(String cacheName, String key);

}
