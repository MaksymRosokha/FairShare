package com.rosokha.fairshare.dashboard;

import com.rosokha.fairshare.user.User;
import com.rosokha.fairshare.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
    private final UserService userService;
    private static final Logger log = LoggerFactory.getLogger(DashboardController.class);

    @Autowired
    public DashboardController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public String getDashboard(Model model){
        User currentUser = userService.getCurrentUser();

        model.addAttribute("user", currentUser);
        model.addAttribute("isUserAuthenticated", userService.isUserAuthenticated());

        log.debug("Rendering dashboard page");
        return "dashboard/dashboard-page";
    }
}
