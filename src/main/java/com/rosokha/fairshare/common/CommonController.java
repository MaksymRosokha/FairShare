package com.rosokha.fairshare.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommonController {
    private static final Logger log = LoggerFactory.getLogger(CommonController.class);

    @GetMapping("/")
    public String getMainPage(){
        log.info("Rendering main page");
        return "main-page";
    }
}
