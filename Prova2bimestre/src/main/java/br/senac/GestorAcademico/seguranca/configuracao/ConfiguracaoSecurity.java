package br.senac.GestorAcademico.seguranca.configuracao;

import br.senac.GestorAcademico.seguranca.dominio.Papel;
import br.senac.GestorAcademico.seguranca.dominio.jwt.AuthEntryPointJwt;
import br.senac.GestorAcademico.seguranca.dominio.jwt.AuthTokenFilter;
import br.senac.GestorAcademico.seguranca.dominio.jwt.JwtUtils;
import br.senac.GestorAcademico.seguranca.dominio.DefaultUserDetailsService;
import br.senac.GestorAcademico.seguranca.dominio.UsuarioRepositorio;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
public class ConfiguracaoSecurity {

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter(JwtUtils jwtUtils, UserDetailsService userDetailsService) {
        return new AuthTokenFilter(jwtUtils, userDetailsService);
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder myEncoder(){
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public UserDetailsService userDetailsService(UsuarioRepositorio usuarioRepositorio) throws Exception {
        return new DefaultUserDetailsService(usuarioRepositorio);
    }

    @Bean
    public SecurityFilterChain myFilterChain(HttpSecurity http,
                                             AuthEntryPointJwt authenticationEntryPoint,
                                             DaoAuthenticationProvider authenticationProvider,
                                             AuthTokenFilter authenticationJwtTokenFilter) throws Exception {
        http
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers(antMatcher("/publico/**")).permitAll()
                        .requestMatchers(antMatcher("/geral/**")).hasAnyRole(Papel.getNomesPapeis())
                        .requestMatchers(antMatcher("/restrito/**")).hasAnyRole(Papel.DIRETOR.name(), Papel.COORDENADOR.name())
                        .requestMatchers(antMatcher("/admin/**")).hasRole(Papel.DIRETOR.name())
                        .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPoint))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(authenticationJwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}