package com.fnklabs.flene.ignite.dao;

import com.fnklabs.flene.core.Period;
import com.fnklabs.flene.core.dao.ClusterHistoryMetricsDao;
import com.fnklabs.flene.core.entity.ClusterHistoryMetrics;
import com.fnklabs.flene.core.entity.NodeHistoryMetrics;
import org.apache.ignite.Ignite;
import org.apache.ignite.cluster.*;
import org.apache.ignite.cluster.ClusterMetrics;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
class ClusterHistoryMetricsDaoImpl implements ClusterHistoryMetricsDao {
    private final Ignite ignite;

    @Autowired
    ClusterHistoryMetricsDaoImpl(Ignite ignite) {
        this.ignite = ignite;
    }


    @Override
    public Collection<ClusterHistoryMetrics> getClusterMetrics(Period period, DateTime from, DateTime to) {
        return null;
    }

    @Override
    public void saveClusterMetrics(Period period, DateTime date, ClusterMetrics clusterMetrics) {

    }

    @Override
    public Collection<NodeHistoryMetrics> getNodeMetrics(ClusterNode clusterNode, Period period, DateTime from, DateTime to) {
        return null;
    }

    @Override
    public void saveNodeMetrics(ClusterNode clusterNode, Period period, DateTime date, ClusterMetrics clusterMetrics) {

    }
}
