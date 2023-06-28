package com.angga.springbootjwtmysqlsimple.api;

import java.security.NoSuchAlgorithmException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.angga.springbootjwtmysqlsimple.api.entity.User;
import com.angga.springbootjwtmysqlsimple.api.entity.UserRepository;
import com.angga.springbootjwtmysqlsimple.api.payloads.request.SignupRequest;
import com.angga.springbootjwtmysqlsimple.api.payloads.request.UpdateUserRequest;
import com.angga.springbootjwtmysqlsimple.api.payloads.response.SignupResponse;
import com.angga.springbootjwtmysqlsimple.api.services.UserService;
import com.angga.springbootjwtmysqlsimple.api.util.JwtUtil;

@RestController
public class RegisterController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest)
            throws NoSuchAlgorithmException {
        var password = UserService.hashedString(signUpRequest.getPassword());
        User user = new User(signUpRequest.getUsername(), password, signUpRequest.getPhone());
        user.setName(signUpRequest.getName());
        user.setRoles("admin");
        userRepository.save(user);
        return ResponseEntity.ok(new SignupResponse("success"));
    }

    @PostMapping("/register/update")
    public ResponseEntity<?> registerUpdateUser(@Valid @RequestBody UpdateUserRequest signUpRequest)
            throws NoSuchAlgorithmException {
        var token = userService.getBearerTokenHeader();

        // Get phone number from JWT
        var jwt = new JwtUtil();
        String username = jwt.extractUsername(token);

        var userDetail = userService.loadUserByUsername(username);
        boolean isTokenValid = jwt.validateToken(token, userDetail);
        if (false == isTokenValid) {
            return ResponseEntity.badRequest().build();
        }

        // update name
        User user = new User();
        user.setUsername(username);
        user.setName(signUpRequest.getName());
        userService.updateName(user, signUpRequest.getName());

        return ResponseEntity.ok(new SignupResponse("success"));
    }
}
