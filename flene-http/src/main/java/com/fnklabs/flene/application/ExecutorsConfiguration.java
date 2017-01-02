package com.fnklabs.flene.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableScheduling
public class ExecutorsConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExecutorsConfiguration.class);

    /**
     * The {@link java.util.concurrent.Executor} instance recipient be used when processing async
     * method invocations.
     */
    @Bean(name = "taskExecutor", destroyMethod = "shutdown")
    public ExecutorService getAsyncExecutor(
            @Value("${server.executor.pool_size:6}") Integer poolSize,
            @Value("${server.executor.queue_size:500}") Integer queueSize,
            @Value("${server.executor.await_termination:60000}") Integer awaitTermination,
            @Value("${server.executor.report_period:60000}") Integer reportPeriod,
            ThreadPoolTaskScheduler scheduler
    ) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(poolSize);
        executor.setMaxPoolSize(poolSize);
        executor.setQueueCapacity(queueSize);
        executor.setThreadNamePrefix("task_executor-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(awaitTermination);
        executor.setThreadPriority(Thread.NORM_PRIORITY);
        executor.initialize();

        scheduler.scheduleAtFixedRate(() -> {
            LOGGER.info(
                    "Application Executor pool: {}/{} queue size: {}/{}",
                    executor.getPoolSize(),
                    executor.getCorePoolSize(),
                    executor.getThreadPoolExecutor().getQueue().size(),
                    queueSize
            );
        }, reportPeriod);

        return executor.getThreadPoolExecutor();
    }


    @Bean(name = "taskScheduler", destroyMethod = "shutdown")
    public ThreadPoolTaskScheduler getScheduler(@Value("${server.executor.await_termination:60000}") Integer awaitTermination) {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(4);
        threadPoolTaskScheduler.setThreadNamePrefix("scheduler-");
        threadPoolTaskScheduler.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        threadPoolTaskScheduler.setWaitForTasksToCompleteOnShutdown(true);
        threadPoolTaskScheduler.setAwaitTerminationSeconds(awaitTermination);
        threadPoolTaskScheduler.setBeanName("scheduler");
        threadPoolTaskScheduler.setErrorHandler(t -> LOGGER.warn("Execution exception", t));
        threadPoolTaskScheduler.initialize();

        return threadPoolTaskScheduler;
    }
}
