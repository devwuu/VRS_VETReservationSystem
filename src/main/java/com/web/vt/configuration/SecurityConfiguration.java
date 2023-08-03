package com.web.vt.configuration;

import com.web.vt.domain.employee.EmployeeService;
import com.web.vt.domain.user.AdminService;
import com.web.vt.security.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final EmployeeService employeeService;
    private final AdminService adminService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AdminDetailService adminDetailService(){
        return new AdminDetailService(adminService);
    }

    @Bean
    public EmployeeDetailService employeeDetailService(){
        return new EmployeeDetailService(employeeService);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(Arrays.asList("localhost:8080"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
        configuration.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public ClientAuthenticationFilter clientAuthenticationFilter(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(employeeDetailService());
        authProvider.setPasswordEncoder(passwordEncoder());
        ProviderManager providerManager = new ProviderManager(authProvider);
        ClientAuthenticationFilter clientAuthenticationFilter = new ClientAuthenticationFilter(providerManager);
        clientAuthenticationFilter.setAuthenticationManager(providerManager);
        clientAuthenticationFilter.setFilterProcessesUrl("/client/token");
        return clientAuthenticationFilter;
    }

    @Bean
    public ClientAuthorizationFilter clientAuthorizationFilter(){
        return new ClientAuthorizationFilter(employeeDetailService());
    }


    @Bean
    public AdminAuthenticationFilter adminAuthenticationFilter(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(adminDetailService());
        authProvider.setPasswordEncoder(passwordEncoder());
        ProviderManager providerManager = new ProviderManager(authProvider);
        AdminAuthenticationFilter adminAuthenticationFilter = new AdminAuthenticationFilter(providerManager);
        adminAuthenticationFilter.setAuthenticationManager(providerManager);
        adminAuthenticationFilter.setFilterProcessesUrl("/admin/token");
        adminAuthenticationFilter.setPostOnly(true);
        return adminAuthenticationFilter;
    }

    @Bean
    public AdminAuthorizationFilter adminAuthorizationFilter(){
        return new AdminAuthorizationFilter(adminDetailService());
    }

    @Bean
    public SecurityFilterChain clientFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatchers((matchers) -> matchers
                        .requestMatchers("client/**", "v1/client/**")
                )
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize ->
                        authorize.
                                requestMatchers("v1/client/**").hasRole("ADMIN")
                                .requestMatchers("client/token").permitAll()
                                .anyRequest().authenticated())
                .cors(httpSecurityCorsConfigurer ->
                        httpSecurityCorsConfigurer
                                .configurationSource(corsConfigurationSource()))
                .formLogin(AbstractHttpConfigurer::disable)
                .addFilter(clientAuthenticationFilter())
                .addFilterBefore(clientAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public SecurityFilterChain adminFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatchers((matchers) -> matchers
                        .requestMatchers("admin/**", "v1/admin/**")
                )
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("v1/admin/**").authenticated()
                                .requestMatchers("admin/token").permitAll()
                                .anyRequest().authenticated())
                .cors(httpSecurityCorsConfigurer ->
                        httpSecurityCorsConfigurer
                                .configurationSource(corsConfigurationSource()))
                .formLogin(AbstractHttpConfigurer::disable)
                .addFilter(adminAuthenticationFilter())
                .addFilterBefore(adminAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
