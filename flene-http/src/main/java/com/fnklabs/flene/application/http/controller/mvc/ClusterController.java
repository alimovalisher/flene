package com.fnklabs.flene.application.http.controller.mvc;

import com.fnklabs.flene.core.Flene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/cluster")
public class ClusterController {
    private final Flene flene;

    @Autowired
    public ClusterController(Flene flene) {
        this.flene = flene;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String info(ModelMap modelMap) {
        modelMap.put("clusterMetrics", flene.getClusterMetrics());
        modelMap.put("nodes", flene.getNodes());

        return "cluster_info";
    }
}
