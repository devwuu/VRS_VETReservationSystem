package com.web.vt.configuration;

import com.web.vt.domain.employee.EmployeeService;
import com.web.vt.domain.user.AdminService;
import com.web.vt.security.FilterExceptionHandler;
import com.web.vt.security.JwtService;
import com.web.vt.security.admin.AdminAuthenticationFilter;
import com.web.vt.security.admin.AdminAuthorizationFilter;
import com.web.vt.security.admin.AdminDetailService;
import com.web.vt.security.client.ClientAuthenticationFilter;
import com.web.vt.security.client.ClientAuthorizationFilter;
import com.web.vt.security.client.EmployeeDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AdminDetailService adminDetailService(AdminService service){
        return new AdminDetailService(service);
    }

    @Bean
    public EmployeeDetailService employeeDetailService(EmployeeService service){
        return new EmployeeDetailService(service);
    }

    @Bean
    public FilterExceptionHandler filterExceptionHandler(){
        return new FilterExceptionHandler();
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
    public ClientAuthenticationFilter clientAuthenticationFilter(EmployeeDetailService employeeDetailService, JwtService jwtService){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(employeeDetailService);
        authProvider.setPasswordEncoder(passwordEncoder());
        ProviderManager providerManager = new ProviderManager(authProvider);
        ClientAuthenticationFilter clientAuthenticationFilter = new ClientAuthenticationFilter(providerManager, jwtService, employeeDetailService);
        clientAuthenticationFilter.setAuthenticationManager(providerManager);
        clientAuthenticationFilter.setFilterProcessesUrl("/client/token");
        return clientAuthenticationFilter;
    }

    @Bean
    public ClientAuthorizationFilter clientAuthorizationFilter(EmployeeDetailService detailService, JwtService jwtService){
        return new ClientAuthorizationFilter(detailService, jwtService);
    }

    @Bean
    public AdminAuthenticationFilter adminAuthenticationFilter(AdminDetailService detailService, JwtService jwtService){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(detailService);
        authProvider.setPasswordEncoder(passwordEncoder());
        ProviderManager providerManager = new ProviderManager(authProvider);
        AdminAuthenticationFilter adminAuthenticationFilter = new AdminAuthenticationFilter(providerManager, jwtService, detailService);
        adminAuthenticationFilter.setAuthenticationManager(providerManager);
        adminAuthenticationFilter.setFilterProcessesUrl("/admin/token");
        adminAuthenticationFilter.setPostOnly(true);
        return adminAuthenticationFilter;
    }

    @Bean
    public AdminAuthorizationFilter adminAuthorizationFilter(AdminDetailService detailService, JwtService jwtService){
        return new AdminAuthorizationFilter(detailService, jwtService);
    }

    // todo logout
    @Bean
    public SecurityFilterChain clientFilterChain(HttpSecurity http, ClientAuthenticationFilter authenticationFilter, ClientAuthorizationFilter authorizationFilter) throws Exception {
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
                .exceptionHandling(handler -> handler.authenticationEntryPoint((request, response, authException) -> log.info("aaaaaa??")))
                .cors(httpSecurityCorsConfigurer ->
                        httpSecurityCorsConfigurer
                                .configurationSource(corsConfigurationSource()))
                .formLogin(AbstractHttpConfigurer::disable)
                .addFilter(authenticationFilter)
                .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(filterExceptionHandler(), ClientAuthenticationFilter.class)
                .addFilterBefore(filterExceptionHandler(), ClientAuthorizationFilter.class);

        return http.build();
    }

    // todo logout
    @Bean
    public SecurityFilterChain adminFilterChain(HttpSecurity http, AdminAuthenticationFilter authenticationFilter, AdminAuthorizationFilter authorizationFilter) throws Exception {
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
                .addFilter(authenticationFilter)
                .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(filterExceptionHandler(), AdminAuthenticationFilter.class)
                .addFilterBefore(filterExceptionHandler(), AdminAuthorizationFilter.class);

        return http.build();
    }

}
