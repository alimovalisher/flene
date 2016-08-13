package com.fnklabs.flene;

import org.apache.ignite.cluster.ClusterMetrics;
import org.apache.ignite.cluster.ClusterNode;

import java.util.Collection;
import java.util.Date;

public interface HistoryMetricsDao {

    Collection<ClusterHistoryMetrics> getClusterMetrics(Period period, Date from, Date to);

    void saveClusterMetrics(ClusterMetrics clusterMetrics, Period period, Date date);

    Collection<NodeHistoryMetrics> getNodeMetrics(ClusterNode clusterNode, Period period, Date from, Date to);

    void saveNodeMetrics(ClusterMetrics clusterMetrics, ClusterNode clusterNode, Period period, Date date);

}
