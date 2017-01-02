package com.fnklabs.flene.application.http.controller.mvc;

import com.fnklabs.flene.core.Flene;
import org.apache.ignite.cache.CacheMetrics;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cache")
public class CacheController {
    private final Flene flene;

    @Autowired
    public CacheController(Flene flene) {
        this.flene = flene;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String list(ModelMap modelMap) {
        List<CacheConfiguration> caches = flene.getCaches();

        modelMap.put("caches", caches);

        return "cache_list";
    }

    @RequestMapping(value = "/{name:.+}", method = RequestMethod.GET)
    public String show(@PathVariable("name") String name, ModelMap modelMap) {
        CacheConfiguration cacheConfiguration = flene.cacheConfiguration(name);
        CacheMetrics cacheMetrics = flene.cacheMetrics(name);
        Map<ClusterNode, CacheMetrics> cacheMetricsByNode = flene.cacheMetricsByNode(name);

        modelMap.put("configuration", cacheConfiguration);
        modelMap.put("metrics", cacheMetrics);
        modelMap.put("cacheMetricsByNode", cacheMetricsByNode);
        modelMap.put("owners", cacheMetricsByNode.keySet());


        return "cache_show";
    }

    @ResponseBody
    @RequestMapping(value = "/metrics/{name:.+}", method = RequestMethod.GET)
    public CacheMetrics cacheMetrics(@PathVariable("name") String name, ModelMap modelMap) {
        return flene.cacheMetrics(name);
    }
}
