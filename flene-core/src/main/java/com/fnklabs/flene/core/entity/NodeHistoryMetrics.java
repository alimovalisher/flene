package com.fnklabs.flene.core.entity;

import com.fnklabs.flene.core.Period;
import org.apache.ignite.cluster.ClusterNode;

import java.util.Date;

public interface NodeHistoryMetrics {
    ClusterNode getClusterNode();

    Period getPeriod();

    Date getDateTime();
}
