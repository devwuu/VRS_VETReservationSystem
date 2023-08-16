package com.web.vt.configuration;

import com.web.vt.domain.employee.EmployeeService;
import com.web.vt.domain.user.AdminService;
import com.web.vt.security.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
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
    public UserLogoutHandler userLogoutHandler(JwtService jwtService){
        return new UserLogoutHandler(jwtService);
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
    public UserAuthenticationFilter adminAuthenticationFilter(PasswordEncoder passwordEncoder, AdminDetailService userDetailsService, JwtService jwtService){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        authenticationProvider.setUserDetailsService(userDetailsService);
        ProviderManager providerManager = new ProviderManager(authenticationProvider);
        UserAuthenticationFilter userAuthenticationFilter = new UserAuthenticationFilter(providerManager, jwtService, userDetailsService);
        userAuthenticationFilter.setAuthenticationManager(providerManager);
        userAuthenticationFilter.setFilterProcessesUrl("/admin/token");
        userAuthenticationFilter.setPostOnly(true);
        return userAuthenticationFilter;
    }

    @Bean
    public UserAuthenticationFilter clientAuthenticationFilter(PasswordEncoder passwordEncoder, EmployeeDetailService userDetailsService, JwtService jwtService){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        authenticationProvider.setUserDetailsService(userDetailsService);
        ProviderManager providerManager = new ProviderManager(authenticationProvider);
        UserAuthenticationFilter userAuthenticationFilter = new UserAuthenticationFilter(providerManager, jwtService, userDetailsService);
        userAuthenticationFilter.setAuthenticationManager(providerManager);
        userAuthenticationFilter.setFilterProcessesUrl("/client/token");
        userAuthenticationFilter.setPostOnly(true);
        return userAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain clientFilterChain(HttpSecurity http,
                                                 @Qualifier("clientAuthenticationFilter") UserAuthenticationFilter authenticationFilter,
                                                 EmployeeDetailService detailService,
                                                 JwtService jwtService,
                                                 UserLogoutHandler logoutHandler,
                                                 FilterExceptionHandler exceptionHandler) throws Exception {
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
                .logout(logoutConfigurer ->
                        logoutConfigurer
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler(logoutHandler)
                                .logoutUrl("/client/logout")
                                .invalidateHttpSession(true)
                                .permitAll()
                )
                .addFilter(authenticationFilter)
                .addFilterBefore(new UserAuthorizationFilter(detailService, jwtService), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(exceptionHandler, UserAuthenticationFilter.class)
                .addFilterBefore(exceptionHandler, UserAuthorizationFilter.class);

        return http.build();
    }

    @Bean
    public SecurityFilterChain adminFilterChain(HttpSecurity http,
                                                @Qualifier("adminAuthenticationFilter") UserAuthenticationFilter authenticationFilter,
                                                AdminDetailService detailService,
                                                JwtService jwtService,
                                                UserLogoutHandler logoutHandler,
                                                FilterExceptionHandler exceptionHandler) throws Exception {
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
                .logout(logoutConfigurer ->
                        logoutConfigurer
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler(logoutHandler)
                                .logoutUrl("/admin/logout")
                                .invalidateHttpSession(true)
                                .permitAll()
                )
                .addFilter(authenticationFilter)
                .addFilterBefore(new UserAuthorizationFilter(detailService, jwtService), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(exceptionHandler, UserAuthenticationFilter.class)
                .addFilterBefore(exceptionHandler, UserAuthorizationFilter.class);

        return http.build();
    }

}
