package com.rosokha.fairshare.common;

import com.rosokha.fairshare.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommonController {
    private static final Logger log = LoggerFactory.getLogger(CommonController.class);
    private final UserService userService;

    @Autowired
    public CommonController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String getMainPage(Model model){
        model.addAttribute("isUserAuthenticated", userService.isUserAuthenticated());

        log.debug("Rendering main page");
        return "main-page";
    }
}
