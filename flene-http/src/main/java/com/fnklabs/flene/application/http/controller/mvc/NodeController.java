package com.fnklabs.flene.application.http.controller.mvc;

import com.fnklabs.flene.core.Flene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.UUID;


@Controller
@RequestMapping("/node")
public class NodeController {
    private final Flene flene;

    @Autowired
    public NodeController(Flene flene) {
        this.flene = flene;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String list(ModelMap modelMap) {
        modelMap.put("nodes", flene.getNodes());

        return "node_list";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") UUID id, ModelMap modelMap) {
        modelMap.put("node", flene.node(id));

        return "node_show";
    }
}
