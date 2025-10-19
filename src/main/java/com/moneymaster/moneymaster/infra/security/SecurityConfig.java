package com.moneymaster.moneymaster.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
//                Disable the csrf token validation
                .csrf(csrf -> csrf.disable())
//                Defines which routes will require authentication
                .authorizeHttpRequests(request -> request
                        .requestMatchers(HttpMethod.GET, "/api/user/users").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/user/register", "/api/user/login")
                        .permitAll()
                        .anyRequest().authenticated())
//                Enables login form on the browser
//                .formLogin(Customizer.withDefaults())
//                Enables rest API accesses (Postman/Insomnia)
//                .httpBasic(Customizer.withDefaults())
//                Defines the application as STATELESS, this means that every authentication will generate a new session id
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    //When the user sends his credential, the object itself is not authenticate
    //To authenticate, this object pass through an Authentication Provider, that do some validations, connects to the database, etc
    //Then, based on our configuration, it will validate the user credentials and then return the result as an Authentication Object, but this time authenticate.
    //This Authentication Provider can be customized as we want.
    @Bean
    public AuthenticationProvider authenticationProvider(){
        //Instantiating the provider(DAO)
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        //Setting the password to be plain text
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("https://moneymaster-3a1s.onrender.com"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Apply this configuration to all paths
        return source;
    }
}
