package com.fnklabs.flene;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FleneScheduler {
    private final Flene flene;
    private final ScheduledExecutorService scheduledExecutorService;

    public FleneScheduler(Flene flene) {
        this.flene = flene;
        this.scheduledExecutorService = Executors.newScheduledThreadPool(2, new ThreadFactory("flene"));
    }

    public FleneScheduler(Flene flene, ScheduledExecutorService scheduledExecutorService) {
        this.flene = flene;
        this.scheduledExecutorService = scheduledExecutorService;
    }

    public void start() {
        for (Period period : Period.values()) {
            scheduledExecutorService.scheduleAtFixedRate(new HistoryUpdater(flene, period), 0, 5, TimeUnit.SECONDS);
        }
    }
}
