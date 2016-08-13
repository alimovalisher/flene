package com.fnklabs.flene;

import org.apache.ignite.cluster.ClusterNode;

import java.util.Date;
import java.util.UUID;

public interface NodeHistoryMetrics {
    ClusterNode getClusterNode();

    Period getPeriod();

    Date getDateTime();
}
