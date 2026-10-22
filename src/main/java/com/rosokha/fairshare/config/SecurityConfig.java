package com.rosokha.fairshare.config;

import com.rosokha.fairshare.user.User;
import com.rosokha.fairshare.user.UserRepository;
import com.rosokha.fairshare.user.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collections;
import java.util.Set;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserRepository userRepository;

    @Autowired
    public SecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterchain(HttpSecurity http) {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/registration", "/css/**").permitAll()
                        .requestMatchers("/home/**").hasAnyRole(UserRole.USER.name(), UserRole.ADMIN.name())
                        .requestMatchers("/admin/**").hasRole(UserRole.ADMIN.name())
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                        .usernameParameter("email")
                        .defaultSuccessUrl("/home", true))
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .permitAll())
                .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
                User user = userRepository
                        .findByEmailIgnoreCase(userEmail)
                        .orElseThrow(() ->
                                new UsernameNotFoundException("User with email = " + userEmail + " not found")
                        );
                Set<SimpleGrantedAuthority> roles = Collections.singleton(user.getRole().toAuthority());
                return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), roles);
            }
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }
}
