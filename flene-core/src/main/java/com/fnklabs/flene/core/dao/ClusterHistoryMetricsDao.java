package com.fnklabs.flene.core.dao;

import com.fnklabs.flene.core.entity.NodeHistoryMetrics;
import com.fnklabs.flene.core.Period;
import com.fnklabs.flene.core.entity.ClusterHistoryMetrics;
import org.apache.ignite.cluster.ClusterMetrics;
import org.apache.ignite.cluster.ClusterNode;
import org.joda.time.DateTime;

import java.util.Collection;

public interface ClusterHistoryMetricsDao {

    Collection<ClusterHistoryMetrics> getClusterMetrics(Period period, DateTime from, DateTime to);

    void saveClusterMetrics(Period period, DateTime date, ClusterMetrics clusterMetrics);

    Collection<NodeHistoryMetrics> getNodeMetrics(ClusterNode clusterNode, Period period, DateTime from, DateTime to);

    void saveNodeMetrics(ClusterNode clusterNode, Period period, DateTime date, ClusterMetrics clusterMetrics);
}
