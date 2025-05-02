package org.lessons.java.spring_la_mia_pizzeria_crud.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    
    @Bean
    @SuppressWarnings("removal")
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
        .requestMatchers("pizze/create","pizze/edit/**").hasRole("ADMIN")
        .requestMatchers(HttpMethod.POST,"pizze/**").hasRole("ADMIN")
        .requestMatchers("/ingredienti","/ingredienti/**").hasRole("ADMIN")
        .requestMatchers("/pizze","/pizze/**").hasAnyRole("USER","ADMIN")
        .requestMatchers("/**").permitAll()
        .and().formLogin()
        .and().logout()
        .and().exceptionHandling();


        return http.build();
    }

    @Bean
    @SuppressWarnings("deprecation")
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        // user detail service
        authProvider.setUserDetailsService(userDetailService());

        //password encoder
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
        
    }

    @Bean
    DatabaseUserDetailService userDetailService() {
        return new DatabaseUserDetailService();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
