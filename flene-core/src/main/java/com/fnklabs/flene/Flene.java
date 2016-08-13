package com.fnklabs.flene;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.CacheMetrics;
import org.apache.ignite.cluster.ClusterGroup;
import org.apache.ignite.cluster.ClusterMetrics;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.lang.IgniteFuture;
import org.apache.ignite.lang.IgniteInClosure;
import org.joda.time.DateTime;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
        historyMetricsDao.saveClusterMetrics(period, period.getStartOfPeriod(DateTime.now()), getClusterMetrics());
    }

    public Map<String, ClusterMetrics> getNodesMetrics() {
        Collection<ClusterNode> nodes = getNodes();

        return nodes.stream()
                    .collect(
                            Collectors.toMap(
                                    clusterNode -> clusterNode.addresses().stream().findFirst().orElse(""),
                                    ClusterNode::metrics,
                                    (a, b) -> a
                            )
                    );
    }


    public Map<String, CacheMetrics> getCachesMetrics() {
        return ignite.cacheNames()
                     .stream()
                     .collect(Collectors.toMap(
                             cacheName -> cacheName == null ? "" : cacheName,
                             cacheName -> {
                                 IgniteCache<Object, Object> cache = ignite.cache(cacheName);
                                 cache.getConfiguration(CacheConfiguration.class).setStatisticsEnabled(true);

                                 return cache.metrics(ignite.cluster().forServers());
                             },
                             (a, b) -> a
                     ));
    }

    public Map<String, Map<String, CacheMetrics>> getCacheNodesMetrics() {
        Map<String, Map<String, CacheMetrics>> metritcs = ignite.cacheNames()
                                                               .stream()
                                                               .collect(Collectors.toMap(
                                                                       cacheName -> cacheName,
                                                                       cacheName -> {
                                                                           Collection<ClusterNode> nodes = ignite.cluster().forServers().nodes();

                                                                           return nodes.stream()
                                                                                       .collect(
                                                                                               Collectors.toMap(
                                                                                                       node -> node.addresses().stream().findFirst().orElse(""),
                                                                                                       node -> {
                                                                                                           ClusterGroup clusterGroup = ignite.cluster().forNode(node);
                                                                                                           return ignite.cache(cacheName).metrics(clusterGroup);
                                                                                                       },
                                                                                                       (a, b) -> a
                                                                                               )
                                                                                       );
                                                                       },
                                                                       (a, b) -> a
                                                               ));
        return metritcs;
    }

    public ListenableFuture<Boolean> rebalance(String cache) {
        SettableFuture<Boolean> rebalanceFuture = SettableFuture.create();
        IgniteFuture<?> rebalance = ignite.cache(cache).rebalance();
        rebalance.listen((IgniteInClosure<IgniteFuture<?>>) igniteFuture -> rebalanceFuture.set(igniteFuture.isDone()));

        return rebalanceFuture;
    }
}
