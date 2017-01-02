package com.fnklabs.flene.application.http.controller.mvc;

import com.fnklabs.flene.core.Flene;
import org.apache.ignite.services.ServiceDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/service")
public class ServiceController {
    private final Flene flene;

    @Autowired
    public ServiceController(Flene flene) {
        this.flene = flene;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String list(ModelMap modelMap) {
        List<ServiceDescriptor> services = flene.services();
        modelMap.put("services", services);

        return "service_list";
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public String info(@PathVariable("name") String name, ModelMap modelMap) {

        ServiceDescriptor serviceDescriptor = flene.service(name);

        modelMap.put("service", serviceDescriptor);
        modelMap.put("topology", flene.topology(serviceDescriptor));
        modelMap.put("originNode", flene.node(serviceDescriptor.originNodeId()));

        return "service_show";
    }
}
