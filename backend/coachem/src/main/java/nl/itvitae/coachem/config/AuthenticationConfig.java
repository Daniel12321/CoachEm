package nl.itvitae.coachem.config;

import jakarta.servlet.http.HttpServletResponse;
import nl.itvitae.coachem.config.jwt.JWTTokenFilter;
import nl.itvitae.coachem.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class AuthenticationConfig {

    @Bean
    public JWTTokenFilter jwtTokenFilter(UserRepository repo) {
        return new JWTTokenFilter(repo);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationProvider authProvider, JWTTokenFilter filter) throws Exception {
        return http
                .exceptionHandling().authenticationEntryPoint((request, response, ex) ->
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage())).and()
                .cors().and()
                .csrf().disable()
                .authenticationProvider(authProvider)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests()
                    .requestMatchers("/v3/**", "/swagger-ui/**").permitAll()
                    .requestMatchers("/error").anonymous() // Allow anonymous access to /error to enable throwing status exceptions

                    .requestMatchers("/api/auth/register").hasAuthority("HR")
                    .requestMatchers("/api/auth/login").permitAll()
                    .requestMatchers("/api/auth/change_password").hasAuthority("USER")

                    .requestMatchers("/api/category/all").hasAnyAuthority("TRAINEE", "MANAGER", "COACH")
                    .requestMatchers("/api/category/new", "/api/category/delete/**").hasAnyAuthority("COACH", "MANAGER")

                    .requestMatchers("/api/evaluation/trainee", "/api/evaluation/attendee").hasAnyAuthority("TRAINEE", "MANAGER", "COACH")
                    .requestMatchers("/api/evaluation/new", "/api/evaluation/attendee/**").hasAnyAuthority("COACH", "MANAGER")
                    .requestMatchers("/api/evaluation/seen").hasAnyAuthority("TRAINEE", "MANAGER", "COACH")
                    .requestMatchers("/api/evaluation/update/**", "/api/evaluation/delete/**").hasAnyAuthority("COACH", "MANAGER")

                    .requestMatchers("/api/feedback/get/**", "/api/feedback/traineeskill/**").hasAnyAuthority("TRAINEE", "MANAGER", "COACH")
                    .requestMatchers("/api/feedback/seen/**").hasAuthority("TRAINEE")
                    .requestMatchers("/api/feedback/new/**", "/api/feedback/update/**", "/api/feedback/delete/**").hasAnyAuthority("COACH", "MANAGER")

                    .requestMatchers("/api/infochange/new").hasAuthority("USER")
                    .requestMatchers("/api/infochange/get/**", "/api/infochange/all", "/api/infochange/delete/**").hasAuthority("HR")

                    .requestMatchers("/api/invite/new/**").hasAuthority("TRAINEE")
                    .requestMatchers("/api/invite/sent/**", "/api/invite/received/**").hasAnyAuthority("TRAINEE", "MANAGER", "COACH")
                    .requestMatchers("/api/invite/accept/**", "/api/invite/delete/**").hasAnyAuthority("TRAINEE", "MANAGER", "COACH")

                    .requestMatchers("/api/notification/all").hasAuthority("USER")

                    .requestMatchers("/api/person/all").hasAuthority("HR")
                    .requestMatchers("/api/person/update/**", "/api/person/delete/**").hasAuthority("HR")
                    .requestMatchers("/api/person/infochange/**").hasAuthority("HR")
                    .requestMatchers("/api/person/trainees").hasAnyAuthority("COACH", "MANAGER")
                    .requestMatchers("/api/person/**", "/api/person/email/**").hasAuthority("USER")

                    .requestMatchers("/api/progress/new/**", "/api/progress/delete/**").hasAuthority("TRAINEE")
                    .requestMatchers("/api/progress/traineeskill/**").hasAnyAuthority("TRAINEE", "COACH", "MANAGER")

                    .requestMatchers("/api/skill/update/**", "/api/skill/new/**", "/api/skill/delete/**").hasAnyAuthority("COACH", "MANAGER")
                    .requestMatchers("/api/skill/all", "/api/skill/get/**").hasAnyAuthority("TRAINEE", "MANAGER", "COACH")

                    .requestMatchers("/api/traineeskill/user/**", "/api/traineeskill/get/**").hasAnyAuthority("TRAINEE", "COACH", "MANAGER")
                    .requestMatchers("/api/traineeskill/update/**", "/api/traineeskill/download/**").hasAnyAuthority("TRAINEE", "MANAGER", "COACH")
                    .requestMatchers("/api/traineeskill/new/**", "/api/traineeskill/upload/**").hasAuthority("TRAINEE")
                    .requestMatchers("/api/traineeskill/delete/**").hasAnyAuthority("COACH", "MANAGER")
                .anyRequest().authenticated()
                .and().build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository repo) {
        return email -> repo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format("User: %s, not found", email)));
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsService service, PasswordEncoder encoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(service);
        provider.setPasswordEncoder(encoder);

        return provider;
    }
}
