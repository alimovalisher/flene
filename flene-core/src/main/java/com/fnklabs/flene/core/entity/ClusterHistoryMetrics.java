package com.fnklabs.flene.core.entity;

import com.fnklabs.flene.core.Period;
import org.apache.ignite.cluster.ClusterMetrics;

import java.util.Date;

public interface ClusterHistoryMetrics {
    ClusterMetrics getMetrics();

    Period getPeriod();

    Date getDateTime();
}
