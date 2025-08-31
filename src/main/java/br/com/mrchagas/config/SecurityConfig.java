package br.com.mrchagas.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Para poder injetar o AuthenticationManager em serviços
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/css/**").permitAll()
                        .requestMatchers("/clientes/**").hasRole("ADMIN")
                        .requestMatchers("/contas/**").hasAnyRole("ADMIN")
                        .requestMatchers("/usuario/**").hasAnyRole("USER","ADMIN")
                        .requestMatchers("/transacoes/**").hasAnyRole("USER","ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login") // página customizada
                        .defaultSuccessUrl("/poslogin", true) // redireciona após login
                        .permitAll()
                )
                .logout(logout -> logout.permitAll());

        return http.build();
    }
}