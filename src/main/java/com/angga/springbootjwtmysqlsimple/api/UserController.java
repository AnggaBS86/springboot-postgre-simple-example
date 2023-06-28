package com.angga.springbootjwtmysqlsimple.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.angga.springbootjwtmysqlsimple.api.entity.UserRepository;
import com.angga.springbootjwtmysqlsimple.api.payloads.response.UserResponse;
import com.angga.springbootjwtmysqlsimple.api.services.UserService;
import com.angga.springbootjwtmysqlsimple.api.util.JwtUtil;

@RestController
public class UserController {

    /**
     * @var UserService
     */
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/users/name")
    public ResponseEntity<?> getUsername() throws Exception {
        var token = userService.getBearerTokenHeader();

         //Get phone number from JWT
        var jwt = new JwtUtil();
        String username = jwt.extractUsername(token);

        var userDetail = userService.loadUserByUsername(username);
        boolean isTokenValid = jwt.validateToken(token, userDetail);
        if (false == isTokenValid) {
            return ResponseEntity.badRequest().build();
        }

        //check phone number exists
        boolean isUsernameExists = userService.isUsernameExists(username);

        var user = userService.findFirstUsername(username);
        return ResponseEntity.ok(new UserResponse(user));
    }

    
}
