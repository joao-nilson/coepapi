package com.example.sgpoepapi.config;

import com.example.sgpoepapi.security.JwtAuthFilter;
import com.example.sgpoepapi.security.JwtService;
import com.example.sgpoepapi.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private FuncionarioService funcionarioService;

    @Autowired
    private JwtService jwtService;

    @Bean
    public OncePerRequestFilter jwtFilter() {
        return new JwtAuthFilter(jwtService, funcionarioService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.disable())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/aspiracoes/**").permitAll()
                        .requestMatchers("/api/v1/clientes/**").permitAll()
                        .requestMatchers("/api/v1/congelamentos/**").permitAll()
                        .requestMatchers("/api/v1/descongelamentos/**").permitAll()
                        .requestMatchers("/api/v1/diagnosticosgestacao/**").permitAll()
                        .requestMatchers("/api/v1/diagnosticossexagem/**").permitAll()
                        .requestMatchers("/api/v1/dosessemen/**").permitAll()
                        .requestMatchers("/api/v1/embrioes/**").permitAll()
                        .requestMatchers("/api/v1/fivs/**").permitAll()
                        .requestMatchers("/api/v1/machosreprodutores/**").permitAll()
                        .requestMatchers("/api/v1/matrizesdoadoras/**").permitAll()
                        .requestMatchers("/api/v1/matrizesreceptoras/**").permitAll()
                        .requestMatchers("/api/v1/meios/**").permitAll()
                        .requestMatchers("/api/v1/metodoscongelamento/**").permitAll()
                        .requestMatchers("/api/v1/oocitos/**").permitAll()
                        .requestMatchers("/api/v1/oocitosclassificacao/**").permitAll()
                        .requestMatchers("/api/v1/prestadorasservico/**").permitAll()
                        .requestMatchers("/api/v1/projetos/**").permitAll()
                        .requestMatchers("/api/v1/qualidadesembriao/**").permitAll()
                        .requestMatchers("/api/v1/racas/**").permitAll()
                        .requestMatchers("/api/v1/sincronizacoes/**").permitAll()
                        .requestMatchers("/api/v1/transferenciaembrioes/**").permitAll()
                        .requestMatchers("/api/v1/funcionarios/**").permitAll()
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/swagger-resources/**",
                                "/webjars/**").permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
