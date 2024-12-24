package com.example.demo_db.configrure;

import com.example.demo_db.component.CustomAuthenticationEntryPoint;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.cors.CorsConfiguration;


import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

//    @Value("${server.ip}")
//    private String serverIp;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return((request, response, authentication) -> {
            request.getSession().setMaxInactiveInterval(10);
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("result", "로그인 성공");

            CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
            responseData.put("token", csrfToken.getToken());

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonmessage = objectMapper.writeValueAsString(responseData);

            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonmessage);
        });

    };

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return((request, response, authentication) -> {
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("error", "로그인 실패");

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonmessage = objectMapper.writeValueAsString(responseData);

            response.setStatus(401); // HTTP 401 Unauthorized
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonmessage);

        });
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return((request, response, authentication) -> {

            Cookie cookie = new Cookie("JSESSIONID", null);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setMaxAge(0);
            response.addCookie(cookie);

            response.setStatus(200); // 성공 응답 상태 코드
            response.getWriter().write("Logout successful");
        });
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf(csrf->csrf.disable())
            http    .cors(cors->{})
                .authorizeHttpRequests(authorize->
//                    authorize.requestMatchers("/**").permitAll()

//

                                authorize.requestMatchers("/login","/api/**","/api/admin-join", "/api/admin-login", "/api/csrf-token").permitAll()
//                                        .requestMatchers("/admin").hasRole("ADMIN")
//                                        .requestMatchers("/user").hasAnyRole("USER", "ADMIN")
//                                        .requestMatchers("/normal").authenticated()
//                                        .requestMatchers("/master").denyAll()
                                        .anyRequest().authenticated()

                )
                .formLogin(form->
                        form.loginProcessingUrl("/admin-login")
                                .successHandler(authenticationSuccessHandler())
                                .failureHandler(authenticationFailureHandler())
                )
                .logout(logout->
                        logout.logoutUrl("/admin-logout")
                                .logoutSuccessHandler(logoutSuccessHandler())
                                .addLogoutHandler((request, response, authentication) -> {
                                    if(request.getSession()!=null) {
                                        request.getSession().invalidate();
                                    }
                                })
                                .deleteCookies("JSESSIONID"))

                .cors(cors->cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowCredentials(true);
                    config.addAllowedOrigin("http://localhost:3000");
                    config.addAllowedHeader("*");
                    config.addAllowedMethod("*");

                    return config;
                }));
//                .sessionManagement(auth->auth.maximumSessions(1)
//                        .maxSessionsPreventsLogin(false)
//                        .expiredSessionStrategy(event -> {//만료된 세션과 관련된 정보
//                            HttpServletResponse response = event.getResponse();
//                            //만료된 세션과 관련된 HTTP 응답 객체
//                            response.setContentType("application/json");
//                            response.setCharacterEncoding("UTF-8");
//                            SecurityContextHolder.clearContext();
//                            response.getWriter().write("세션이 만료되었습니다. 다시 로그인해주세요.");
//                        }));
        http.exceptionHandling(exception->
                exception.authenticationEntryPoint(this.customAuthenticationEntryPoint));


        return http.build();
    }
}

