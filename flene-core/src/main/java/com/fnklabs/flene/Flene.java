package com.fnklabs.flene;

import org.apache.ignite.Ignite;
import org.apache.ignite.cluster.ClusterMetrics;
import org.apache.ignite.cluster.ClusterNode;

import java.util.Collection;

public class Flene {
    private final Ignite ignite;

    private final HistoryMetricsDao historyMetricsDao;


    public Flene(Ignite ignite, HistoryMetricsDao historyMetricsDao) {
        this.ignite = ignite;
        this.historyMetricsDao = historyMetricsDao;
    }

    public ClusterMetrics getClusterMetrics() {
        return ignite.cluster().forServers().metrics();
    }

    public ClusterMetrics getNodeMetrics(ClusterNode clusterNode) {
        return ignite.cluster().forServers().forNode(clusterNode).metrics();
    }

    public Collection<ClusterNode> getNodes() {
        return ignite.cluster().forServers().nodes();
    }

    public void saveMetrics(Period period) {

    }
}
