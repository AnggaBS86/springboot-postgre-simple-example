package com.angga.springbootjwtmysqlsimple.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.angga.springbootjwtmysqlsimple.api.entity.User;
import com.angga.springbootjwtmysqlsimple.api.entity.UserRepository;
import com.angga.springbootjwtmysqlsimple.api.payloads.request.AuthenticateRequest;
import com.angga.springbootjwtmysqlsimple.api.payloads.response.AuthenticateResponse;
import com.angga.springbootjwtmysqlsimple.api.services.UserService;
import com.angga.springbootjwtmysqlsimple.api.util.JwtUtil;

@RestController
public class AuthController {

	/**
	 * @var AuthenticationManager
	 */
	@Autowired
	private AuthenticationManager authenticationManager;

	/**
	 * @var UserService
	 */
	@Autowired
	private UserService userService;

	@Autowired
	private JwtUtil jwtUtil;

	 @Autowired 
	 UserRepository userRepository;

	/**
	 * Hello world test
	 * 
	 * @return String
	 */
	@RequestMapping(value = "/hello-world")
	public String helloWorld() {
		return "Hello World";
	}

	/**
	 * This method used for authorization purpose
	 * With the username and password that filled in table `user`
	 * 
	 * @param request AuthenticateRequest
	 * @return ResponseEntity<?>
	 * 
	 * @throws Exception
	 */
	@PostMapping("/auth")
	public ResponseEntity<?> creatAuthToken(@RequestBody AuthenticateRequest request) throws Exception {
		try {
			authenticationManager
					.authenticate(
							new UsernamePasswordAuthenticationToken(request.getPhone(), userService.hashedString(request.getPassword())));
		} catch (BadCredentialsException ex) {
			throw new Exception("Incorrect Username and Password combination!", ex);
		}

		final UserDetails userDetails = userService.loadUserByUsername(request.getPhone());
		final String token = jwtUtil.generateToken(userDetails);

		//update jwt token
		User user = new User();
		user.setUsername(request.getPhone());
		userService.update(user, token);

		return ResponseEntity.ok(new AuthenticateResponse(token, "success"));
	}
}
