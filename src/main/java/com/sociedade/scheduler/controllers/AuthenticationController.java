package com.sociedade.scheduler.controllers;

import com.sociedade.scheduler.model.Company;
import com.sociedade.scheduler.model.user.User;
import com.sociedade.scheduler.model.user.dto.AuthenticationDTO;
import com.sociedade.scheduler.model.user.dto.LoginResponseDTO;
import com.sociedade.scheduler.model.user.dto.RegisterDTO;
import com.sociedade.scheduler.model.user.dto.UserDTO;
import com.sociedade.scheduler.repositories.UserRepository;
import com.sociedade.scheduler.security.TokenService;
import com.sociedade.scheduler.services.AuthorizationService;
import io.swagger.annotations.ApiOperation;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;
    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthorizationService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        User user = (User) this.repository.findByLogin(data.login());
        UserDTO userDTO = new UserDTO(user.getId(), user.getLogin(), user.getEmail(), user.getUsername());

        boolean isAdmin = false;

        for (GrantedAuthority role : user.getAuthorities()) {
            if ("ROLE_ADMIN".equals(role.getAuthority())) {
                isAdmin = true;
            }
        }

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token, userDTO));
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody @Valid RegisterDTO data){
        if(this.repository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        List<Company> companies = Collections.emptyList();
        User newUser = new User(null, data.login(), data.email(), encryptedPassword, data.role(), null, false, companies);

        User save = this.repository.save(newUser);


        UserDTO userCreated = new UserDTO(
                save.getId(),
                save.getLogin(),
                save.getEmail(),
                save.getUsername()
        );
        return ResponseEntity.ok().body(userCreated);
    }


    @GetMapping("/companies/{userId}")
    public ResponseEntity<List<Company>> getCompaniesByUser(@PathVariable("userId") String userId) {
        List<Company> companies = authService.getCompaniesByUserId(userId);
        return ResponseEntity.ok(companies);
    }

    @PutMapping("{id}")
    @ApiOperation(value = "Update current user by Id")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("id") String id, UserDTO userUpdated) {
        Optional<User> currentUser = this.authService.findById(id);

        if (currentUser.isPresent()) {
            User us = currentUser.get();
            us.setEmail(userUpdated.email());
            us.setLogin(userUpdated.login());
            this.repository.save(us);
            return ResponseEntity.ok().body(userUpdated);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Set User deleted")
    public ResponseEntity<Boolean> deleteCompany(@PathVariable String id) {
        try {
            this.authService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/validate")
    public ResponseEntity<Boolean>validateToken(
            @RequestHeader(value = "token") String token) {
        System.out.println("token " + token);
        return ResponseEntity.ok(this.authService.validateToken(token));
    }

    @GetMapping(value = "/validate-user/{id}")
    public ResponseEntity<Boolean>validateUserToken(
            @PathVariable(value = "id") String id,
            @RequestHeader(value = "token") String token) {
        System.out.println("token " + token);
        return ResponseEntity.ok(this.authService.validateUserToken(id, token));
    }
}
