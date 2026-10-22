package com.rosokha.fairshare.authorization;

import com.rosokha.fairshare.user.User;
import com.rosokha.fairshare.user.UserRole;
import com.rosokha.fairshare.user.UserService;
import com.rosokha.fairshare.user.dto.UserRegistrationDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class AuthorizationController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthorizationController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String getLoginPage(Model model,
                               @RequestParam(value = "error", required = false) String error) {
        if (userService.isUserAuthenticated()) {
            return "redirect:/home";
        }
        if (error != null ) {
            model.addAttribute("isAuthenticationFailed", true);
            model.addAttribute("errorMessage", "Invalid email or password.");
        }

        return "authorization/login-page";
    }

    @GetMapping("/registration")
    public String getRegisterPage(Model model) {
        if (userService.isUserAuthenticated()) {
            return "redirect:/home";
        }
        model.addAttribute("userDto", new UserRegistrationDto());
        return "authorization/registration-page";
    }

    @PostMapping("/registration")
    public String registerUser(
            Model model,
            @Valid @ModelAttribute("userDto") UserRegistrationDto userDto,
            BindingResult bindingResult,
            HttpServletRequest request
    ) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            model.addAttribute("isAuthenticationFailed", true);
            model.addAttribute("errorMessage", errorMessage);
            return "authorization/registration-page";
        }


        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        User user = new User(
                userDto.getLogin(),
                userDto.getFullName(),
                userDto.getEmail(),
                encodedPassword
        );
        userService.save(user);

        forceAutoLogin(userDto.getEmail(), encodedPassword, request);

        return "redirect:/home";
    }

    private void forceAutoLogin(String email, String password, HttpServletRequest request) {
        try {
            Set<SimpleGrantedAuthority> roles = Collections.singleton(UserRole.USER.toAuthority());
            Authentication authentication = new UsernamePasswordAuthenticationToken(email, password, roles);
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);

            HttpSession session = request.getSession(true);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
