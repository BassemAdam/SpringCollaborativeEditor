package Uni.Project.CollaborativeEditorBackend.controller;

import Uni.Project.CollaborativeEditorBackend.model.LoginRequest;
import Uni.Project.CollaborativeEditorBackend.model.LoginResponse;
import Uni.Project.CollaborativeEditorBackend.model.SignUpRequest;
import Uni.Project.CollaborativeEditorBackend.model.User;
import Uni.Project.CollaborativeEditorBackend.service.userService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;

@RestController
@RequestMapping("/auth")
public class LoginSignupController {


    @Autowired
    private userService service;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody SignUpRequest user) {
        User existingUserByUsername = service.findUserByUsername(user.getUsername());
        User existingUserByEmail = service.findUserByEmail(user.getEmail());
        if (existingUserByUsername != null || existingUserByEmail != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());

        // Hash the password
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        newUser.setPassword(hashedPassword);

        newUser = service.addUser(newUser);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        User existingUser = service.findUserByUsername(loginRequest.getUsername());
        if (existingUser != null && passwordEncoder.matches(loginRequest.getPassword(), existingUser.getPassword())) {
            String token = "AUTH_TOKEN_EYBBJJNXS92U8HBS";
            LoginResponse response = new LoginResponse(existingUser,token);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

}

