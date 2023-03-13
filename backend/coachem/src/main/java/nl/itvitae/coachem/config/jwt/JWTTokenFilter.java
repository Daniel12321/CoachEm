package nl.itvitae.coachem.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nl.itvitae.coachem.config.User;
import nl.itvitae.coachem.repository.PersonRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JWTTokenFilter extends OncePerRequestFilter {

    private final PersonRepository repo;

    public JWTTokenFilter(PersonRepository repo) {
        this.repo = repo;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        // Get authorization header and validate
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        // Get jwt token and validate
        final var token = JWTToken.validate(header.split(" ")[1].trim());
        if (!token.isValid()) {
            chain.doFilter(request, response);
            return;
        }

        // Get user identity and set it on the spring security context
        final var user = this.repo.findByEmail(token.getEmail()).map(User::new).orElse(null);
        final var auth = new UsernamePasswordAuthenticationToken(user, null, user == null ? List.of() : user.getAuthorities());

        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(auth);
        chain.doFilter(request, response);
    }
}
