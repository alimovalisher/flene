package com.fnklabs.flene;

import org.apache.ignite.cluster.ClusterMetrics;
import org.apache.ignite.cluster.ClusterNode;
import org.joda.time.DateTime;

import java.util.Collection;

public interface HistoryMetricsDao {

    Collection<ClusterHistoryMetrics> getClusterMetrics(Period period, DateTime from, DateTime to);

    void saveClusterMetrics(Period period, DateTime date, ClusterMetrics clusterMetrics);

    Collection<NodeHistoryMetrics> getNodeMetrics(ClusterNode clusterNode, Period period, DateTime from, DateTime to);

    void saveNodeMetrics(ClusterNode clusterNode, Period period, DateTime date, ClusterMetrics clusterMetrics);

}
