package org.step.linked.step.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    @Value("${admin.username}")
//    private String username;
//    @Value("${admin.password}")
//    private String password;

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder,
                          @Qualifier("userServiceImpl") UserDetailsService userDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .inMemoryAuthentication()
//                .withUser(username)
//                .password(passwordEncoder.encode(password))
//                .roles("ADMIN");
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests() // все запросы должны быть авторизованы (нет анонимности)
//                .antMatchers(HttpMethod.GET, "/api/v1/profiles/public").permitAll() // дать разрешение всем
                .antMatchers("/api/v1/public/**")
                .permitAll() // дать разрешение любым урлам после /api/v1/public/
                .antMatchers(HttpMethod.POST, "/api/v1/authentication/*")
                .permitAll()
                .anyRequest() // все запросы
                .authenticated() // должны быть аутентифицированы
                .and() // и
                .formLogin() // базовая страница логина спринга
                .and() // и
                .logout() // логаут /logout
                .and() // и
                .httpBasic()
                .and()
                .cors(httpSecurityCorsConfigurer -> {
                    CorsConfiguration corsConfiguration = new CorsConfiguration();

                    corsConfiguration.setAllowedHeaders(Arrays.asList(
//                            "Authorization", "Description", "Content-Type"
                            "*"
                    ));
                    corsConfiguration.setAllowedOrigins(Arrays.asList(
//                            "127.0.0.1", "http://super.server.com"
                            "*"
                    ));
                    corsConfiguration.setAllowedMethods(Arrays.asList(
//                            "POST", "GET", "PUT", "DELETE" // HEADER, OPTION...
                            "*"
                    ));
                    corsConfiguration.setAllowCredentials(true);
                    corsConfiguration.setMaxAge(3600L);
                })
                .csrf()
                .disable();
    }
}