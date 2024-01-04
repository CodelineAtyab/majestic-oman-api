package com.majesticoman.api.configration;

mport org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;


    @Configuration
    @EnableWebSecurity
    @EnableMethodSecurity
    public class SecurityConfigration {
        @Bean
        public UserDetailsService userDetailsService(){
            PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
            InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
            manager.createUser(User.withUsername("admin").password(encoder.encode("1234")).roles("ADMIN").build());
            return manager;
        }
        @Bean
        public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
            return http.getSharedObject(AuthenticationManager.class);
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests();
            registry.requestMatchers(req -> req.getMethod().equalsIgnoreCase("OPTIONS")).permitAll();

            http
                    .csrf(AbstractHttpConfigurer::disable)
                    .authorizeRequests((authz) -> authz
                            .requestMatchers(HttpMethod.GET,"/api/v1/**").permitAll()
                            .requestMatchers(HttpMethod.POST,"/api/v1/login").permitAll()
                            .anyRequest().authenticated()
                    )
                    .httpBasic(withDefaults())
                    .cors(withDefaults());

            return http.build();
        }
    }

}
