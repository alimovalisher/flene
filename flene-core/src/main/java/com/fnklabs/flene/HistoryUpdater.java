package com.fnklabs.flene;

class HistoryUpdater implements Runnable {
    private final Flene flene;
    private final Period period;

    public HistoryUpdater(Flene flene, Period period) {
        this.flene = flene;
        this.period = period;
    }

    @Override
    public void run() {
        flene.saveMetrics(period);
    }
}
