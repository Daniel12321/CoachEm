package nl.itvitae.coachem.service;

import jakarta.transaction.Transactional;
import nl.itvitae.coachem.config.jwt.JWTToken;
import nl.itvitae.coachem.dto.PasswordChangeRequestDto;
import nl.itvitae.coachem.dto.PersonDto;
import nl.itvitae.coachem.dto.auth.LoginRequestDto;
import nl.itvitae.coachem.dto.auth.LoginResponseDto;
import nl.itvitae.coachem.dto.auth.RegisterRequestDto;
import nl.itvitae.coachem.model.Person;
import nl.itvitae.coachem.model.User;
import nl.itvitae.coachem.repository.PersonRepository;
import nl.itvitae.coachem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class AuthService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PersonRepository personRepo;

    @Autowired
    private PersonDto.Mapper mapper;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private PasswordEncoder encoder;

    public Optional<LoginResponseDto> login(LoginRequestDto request) {
        Authentication auth = this.authManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        SecurityContextHolder.getContext().setAuthentication(auth);

        User user = (User) auth.getPrincipal();

        LoginResponseDto resp = new LoginResponseDto(user.getUsername(), user.getRole(), JWTToken.of(auth));

        return Optional.of(resp);
    }

    public Optional<PersonDto> register(RegisterRequestDto dto) {
        if (!dto.isValid())
            return Optional.empty();

        User user = new User(dto.email(), this.encoder.encode(dto.password()), dto.role());
        Person person = new Person(dto.name(), dto.address(), dto.city(), dto.zipcode(), dto.phonenumber());
        person.setUser(user);

        this.userRepo.save(user);
        person = this.personRepo.save(person);

        return Optional.of(this.mapper.get(person));
    }

    public Optional<Integer> changePassword(PasswordChangeRequestDto request) {
        try {
            this.authManager.authenticate(new UsernamePasswordAuthenticationToken(User.getFromAuth().getEmail(), request.oldPassword()));
        } catch (Exception exc) {
            System.out.println("Failed auth check");
            return Optional.of(401);
        }

        if (!request.newPassword1().equals(request.newPassword2()))
            return Optional.of(400);

        User user = User.getFromAuth();
        user.setPassword(this.encoder.encode(request.newPassword1()));
        this.userRepo.save(user);

        return Optional.of(200);
    }
}
