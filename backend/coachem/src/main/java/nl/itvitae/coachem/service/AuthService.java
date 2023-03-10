package nl.itvitae.coachem.service;

import jakarta.transaction.Transactional;
import nl.itvitae.coachem.config.User;
import nl.itvitae.coachem.config.jwt.JWTToken;
import nl.itvitae.coachem.dto.auth.LoginRequestDto;
import nl.itvitae.coachem.dto.auth.LoginResponseDto;
import nl.itvitae.coachem.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class AuthService {

    @Autowired
    private PersonRepository repo;

    @Autowired
    private AuthenticationManager authManager;

    public Optional<LoginResponseDto> login(LoginRequestDto request) {
        Authentication auth = this.authManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        SecurityContextHolder.getContext().setAuthentication(auth);

        User user = (User) auth.getPrincipal();

        LoginResponseDto resp = new LoginResponseDto(user.getUsername(), JWTToken.of(auth));
//        resp.setRefreshToken(JWTToken.refresh(auth));

        return Optional.of(resp);
    }
}
