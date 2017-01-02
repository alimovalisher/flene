package com.fnklabs.flene.application.http.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController implements org.springframework.boot.autoconfigure.web.ErrorController {

    @RequestMapping("/error")
    public String conflict(ModelMap modelMap) {
        return "error_default";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
