package com.shop.ShopApplication.config;

import com.shop.ShopApplication.JWT.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors(Customizer.withDefaults())
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .antMatchers("/api/client/**")
                                .hasAuthority("CLIENT")
                                .antMatchers("/api/menu/client/**")
                                .hasAuthority("CLIENT")
                                .antMatchers("/api/menu/admin/**")
                                .hasAuthority("ADMIN")
                                .antMatchers("/api/admin/**")
                                .hasAuthority("ADMIN")
                                .antMatchers("/api/filial/admin/**")
                                .hasAuthority("ADMIN")
                                .antMatchers("/api/auth/**", "/swagger-ui/**","/swagger-ui.html","/swagger-ui/","/v3/api-docs", "/v3/api-docs/swagger-config", "/api/admin/log", "/api/client/auth/*","/api/admin/*").permitAll()
                                .anyRequest().authenticated()
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
//    @Bean
//    public void addAdmin(){
//        User user = new User();
//        // in sake of redeploy to create an admin (test)
//        if (!userRepository.existsByPhoneNumber("admin")) {
//            user.setPhoneNumber("admin");
//            user.setRole(ERole.getRole(1));
//            user.setActive(true);
//            user.setCompleted(true);
//            user.setActivationCode("$2a$12$HZAXhyLTr9r1tS7/JPPOXO.NuXCB9a2KXM7o0OW0ZK40uLPfzdB.6");
//            userRepository.save(user);
//        }
//    }

    //TODO: creating admin if there is no admin
}
