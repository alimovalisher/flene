package com.fnklabs.flene.application;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteCheckedException;
import org.apache.ignite.IgniteSpring;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMemoryMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.eviction.fifo.FifoEvictionPolicy;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.SimpleCommandLinePropertySource;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Configuration
public class Worker {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.getEnvironment().getPropertySources().addFirst(new SimpleCommandLinePropertySource(args));
        context.register(Worker.class);
        context.refresh();
    }

    @Bean(destroyMethod = "close")
    public Ignite ignite(@Value("${ignite.configuration_url:ignite_test.xml}") String configurationUrl, ApplicationContext applicationContext) throws IgniteCheckedException {
        Ignite ignite = IgniteSpring.start(configurationUrl, applicationContext);

        CacheConfiguration<Object, Object> cacheCfg = new CacheConfiguration<>("test.1");
        cacheCfg.setStatisticsEnabled(true);
        cacheCfg.setBackups(1);
        cacheCfg.setCacheMode(CacheMode.PARTITIONED);
        cacheCfg.setAtomicityMode(CacheAtomicityMode.ATOMIC);
        cacheCfg.setReadFromBackup(false);
        cacheCfg.setMemoryMode(CacheMemoryMode.OFFHEAP_TIERED);
        cacheCfg.setEvictionPolicy(new FifoEvictionPolicy(1));


        IgniteCache<Object, Object> cache = ignite.getOrCreateCache(cacheCfg);


        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(() -> {
            for (int i = 0; i < 10_000; i++) {
                cache.put(i, i);
                cache.get(i);
            }

            for (int i = 0; i < 5_000; i++) {
                cache.remove(i);
            }
        }, 0, 100, TimeUnit.MILLISECONDS);

        ignite.services().deployNodeSingleton("test", new TestService());

        return ignite;
    }
}
