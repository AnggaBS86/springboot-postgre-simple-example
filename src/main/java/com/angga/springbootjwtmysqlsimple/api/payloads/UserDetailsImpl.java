package com.angga.springbootjwtmysqlsimple.api.payloads;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.angga.springbootjwtmysqlsimple.api.entity.User;

/**
 * This class used for core user information
 * 
 * That be taken from
 * `org.springframework.security.core.userdetails.UserDetails`
 * 
 * @author Angga Bayu Sejati<anggabs86@gmail.com>
 */
public class UserDetailsImpl implements UserDetails {

	/**
	 * @var User mUser
	 */
	private User userData;

	public UserDetailsImpl(User user) {
		userData = user;
	}

	/**
	 * Get auth
	 * 
	 * @return Collection
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (userData.getRoles().equalsIgnoreCase("")) {
			return null;
		}
		
		var auth = Arrays.stream(userData.getRoles().split(","))
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
		return auth;
	}

	@Override
	public String getUsername() {
		return userData.getUsername();
	}

	@Override
	public String getPassword() {
		return userData.getPassword();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
}
