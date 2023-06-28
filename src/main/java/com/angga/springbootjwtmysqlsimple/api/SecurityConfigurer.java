package com.angga.springbootjwtmysqlsimple.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.angga.springbootjwtmysqlsimple.api.filter.JwtRequestFilter;
import com.angga.springbootjwtmysqlsimple.api.services.UserService;

/**
 * Provides a convenient base class for creating a WebSecurityConfigurer
 * instance. The implementation
 * allows customization by overriding methods.
 * 
 * Will automatically apply the result of looking up AbstractHttpConfigurer from
 * SpringFactoriesLoader
 * to allow developers to extend the defaults. To do this, you must create a
 * class that extends AbstractHttpConfigurer
 * and then create a file in the classpath at "META-INF/spring.factories"
 * 
 * @author Angga Bayu Sejati<anggabs86@gmail.com>
 */
@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService myUserDetailService;

	@Autowired
	private JwtRequestFilter mJwtReqFilter;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(myUserDetailService);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.authorizeRequests()
				.antMatchers("/auth").permitAll()
				.antMatchers("/register").permitAll()
				.anyRequest().authenticated()
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.addFilterBefore(mJwtReqFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
