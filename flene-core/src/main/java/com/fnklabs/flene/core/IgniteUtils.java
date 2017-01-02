package com.fnklabs.flene.core;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.eviction.fifo.FifoEvictionPolicy;
import org.apache.ignite.configuration.CacheConfiguration;

import java.util.concurrent.TimeUnit;

public class IgniteUtils {

    static final int DEFAULT_BACKUPS = 4;

    public static <K, V> IgniteCache<K, V> getOrCreateCache(Ignite ignite, String name) {
        return ignite.getOrCreateCache(createCacheConfiguration(name));
    }

    public static <K, V> CacheConfiguration<K, V> createCacheConfiguration(String name) {
        CacheConfiguration<K, V> configuration = new CacheConfiguration<>(name);
        configuration.setBackups(DEFAULT_BACKUPS);
        long secondInWeek = TimeUnit.DAYS.convert(7, TimeUnit.SECONDS);
        configuration.setEvictionPolicy(new FifoEvictionPolicy((int) secondInWeek));
        configuration.setAtomicityMode(CacheAtomicityMode.ATOMIC);
        configuration.setCacheMode(CacheMode.PARTITIONED);

        return configuration;
    }
}
