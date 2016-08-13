package com.fnklabs.flene;

import org.apache.ignite.cluster.ClusterMetrics;

import java.util.Date;

public interface ClusterHistoryMetrics {
    ClusterMetrics getMetrics();

    Period getPeriod();

    Date getDateTime();
}
