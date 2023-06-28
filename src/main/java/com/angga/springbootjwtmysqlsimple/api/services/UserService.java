package com.angga.springbootjwtmysqlsimple.api.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.angga.springbootjwtmysqlsimple.api.entity.User;
import com.angga.springbootjwtmysqlsimple.api.entity.UserRepository;
import com.angga.springbootjwtmysqlsimple.api.payloads.UserDetailsImpl;

/**
 * This class used for bridge of services between entities and repository
 * 
 * @author Angga Bayu Sejati<anggabs86@gmail.com>
 */
@Service
public class UserService implements UserDetailsService {

	public static final String SALT = "123";

	@Autowired
	UserRepository userRepository;

	/**
	 * override loadUserByUsername
	 * 
	 * @return UserDetails
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// defensive strategy --> prevent blank `username`
		if (username.trim().equalsIgnoreCase("")) {
			new UsernameNotFoundException("Username cannot be blank!");
		}

		// Get username value
		var userData = userRepository.findByUsername(username);
		userData.orElseThrow(() -> new UsernameNotFoundException("Invalid `username` credential!"));

		return userData.map(UserDetailsImpl::new).get();
	}

	public User getUserPhoneData(String jwt) {
		var user = userRepository.findByJwtCode(jwt);

		return user;
	}

	public List<User> getAllUsers() {
		List<User> users = new ArrayList<User>();
		userRepository.findAll().forEach(user -> users.add(user));
		return users;
	}

	@Transactional
	public void update(User user, String token) {
		userRepository
				.findByUsername(user.getUsername()) // returns Optional<User>
				.ifPresent(user1 -> {
					user1.setJwtCode(token);
					userRepository.save(user1);
				});
	}

	@Transactional
	public void updateName(User user, String name) {
		userRepository
				.findByUsername(user.getUsername()) // returns Optional<User>
				.ifPresent(user1 -> {
					user1.setName(name);
					userRepository.save(user1);
				});
	}

	public static String hashedString(String data) throws NoSuchAlgorithmException {
		MessageDigest messageDigest = MessageDigest.getInstance("MD5");

		String saltedData = data + UserService.SALT;
		messageDigest.update(saltedData.getBytes());
		byte[] digest = messageDigest.digest();
		StringBuffer sb = new StringBuffer();
		for (byte b : digest) {
			sb.append(Integer.toHexString((int) (b & 0xff)));
		}

		return sb.toString();
	}

	public boolean isUsernameExists(String phone) {
		var user = userRepository.findByUsername(phone);

		if (user != null) {
			return true;
		}

		return false;
	}
	
	public User findFirstUsername(String username) {
		return userRepository.findFirstByUsername(username);
	}

	public String getBearerTokenHeader() {
        var bearer = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
                .getHeader("Authorization");

        String[] splited = bearer.split("\\s+");
        return splited[1];
    }
}
