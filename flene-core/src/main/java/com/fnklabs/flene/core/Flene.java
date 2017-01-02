package com.fnklabs.flene.core;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import org.apache.commons.lang3.StringUtils;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.CacheMetrics;
import org.apache.ignite.cluster.ClusterGroup;
import org.apache.ignite.cluster.ClusterMetrics;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.lang.IgniteFuture;
import org.apache.ignite.lang.IgniteInClosure;
import org.apache.ignite.services.ServiceDescriptor;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class Flene {
    private final Ignite ignite;

    @Autowired
    public Flene(Ignite ignite) {
        this.ignite = ignite;
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

    public Map<ClusterNode, ClusterMetrics> getNodesMetrics() {
        Collection<ClusterNode> nodes = getNodes();

        return nodes.stream()
                    .collect(
                            Collectors.toMap(
                                    clusterNode -> clusterNode,
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
                             cacheName -> ignite.cache(cacheName).metrics(),
                             (a, b) -> a
                     ));
    }

    public Map<String, Map<ClusterNode, CacheMetrics>> getCacheNodesMetrics() {
        return ignite.cacheNames()
                     .stream()
                     .collect(Collectors.toMap(
                             cacheName -> cacheName,
                             this::cacheMetricsByNode,
                             (a, b) -> a
                     ));
    }

    public ListenableFuture<Boolean> rebalance(String cache) {
        SettableFuture<Boolean> rebalanceFuture = SettableFuture.create();
        IgniteFuture<?> rebalance = ignite.cache(cache).rebalance();
        rebalance.listen((IgniteInClosure<IgniteFuture<?>>) igniteFuture -> rebalanceFuture.set(igniteFuture.isDone()));

        return rebalanceFuture;
    }

    @Nullable
    public ClusterNode node(UUID id) {
        return ignite.cluster().node(id);
    }

    public List<CacheConfiguration> getCaches() {
        return ignite.cacheNames()
                     .stream()
                     .map(name -> {
                         IgniteCache cache = ignite.cache(name);
                         CacheConfiguration configuration = (CacheConfiguration) cache.getConfiguration(CacheConfiguration.class);

                         return configuration;
                     })
                     .collect(Collectors.toList());
    }

    public CacheConfiguration cacheConfiguration(String name) {
        return ignite.cache(name).getConfiguration(CacheConfiguration.class);
    }

    public CacheMetrics cacheMetrics(String name) {
        return ignite.cache(name).metrics();
    }

    public Map<ClusterNode, CacheMetrics> cacheMetricsByNode(String name) {

        ClusterGroup cacheOwner = ignite.cluster().forCacheNodes(name);

        return cacheOwner.nodes()
                         .stream()
                         .collect(
                                 Collectors.toMap(
                                         node -> node,
                                         node -> {
                                             ClusterGroup nodeGroup = ignite.cluster().forHost(node);

                                             return ignite.cache(name).metrics(nodeGroup);
                                         },
                                         (a, b) -> a
                                 )
                         );
    }

    public List<ServiceDescriptor> services() {
        return new ArrayList<>(ignite.services().serviceDescriptors());
    }

    @Nullable
    public ServiceDescriptor service(String name) {
        return ignite.services()
                     .serviceDescriptors()
                     .stream()
                     .filter(serviceDescriptor -> StringUtils.equals(serviceDescriptor.name(), name))
                     .findFirst()
                     .orElse(null);
    }

    public Map<ClusterNode, Integer> topology(ServiceDescriptor serviceDescriptor) {
        return serviceDescriptor.topologySnapshot()
                                .entrySet()
                                .stream()
                                .collect(Collectors.toMap(
                                        entry -> node(entry.getKey()),
                                        Map.Entry::getValue,
                                        (a, b) -> a
                                ));
    }

    @Scheduled(fixedRate = 500)
    protected void dump() {
    }
}
