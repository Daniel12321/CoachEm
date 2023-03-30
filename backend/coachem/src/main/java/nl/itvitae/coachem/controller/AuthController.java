package nl.itvitae.coachem.controller;

import nl.itvitae.coachem.api.IAuthAPI;
import nl.itvitae.coachem.dto.PersonDto;
import nl.itvitae.coachem.dto.auth.LoginRequestDto;
import nl.itvitae.coachem.dto.auth.LoginResponseDto;
import nl.itvitae.coachem.dto.auth.PasswordChangeRequestDto;
import nl.itvitae.coachem.dto.auth.RegisterRequestDto;
import nl.itvitae.coachem.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class AuthController implements IAuthAPI {

    @Autowired
    private AuthService service;

    @Override
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request) {
        return this.service.login(request)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(401).build());
    }

    @Override
    public ResponseEntity<PersonDto> register(RegisterRequestDto request) {
        return this.service.register(request)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @Override
    public ResponseEntity<Void> changePassword(PasswordChangeRequestDto request) {
        int status = this.service.changePassword(request).orElse(400);
        return ResponseEntity.status(status).build();
    }
}
