package com.angga.springbootjwtmysqlsimple.api.entity;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interfacing UserRepository
 * 
 * This interface used for blueprint of `User` operations
 * 
 * @author Angga Bayu Sejati<anggabs86@gmail.com>
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	/**
	 * Find username by `name`
	 * 
	 * @param username String
	 * @return Optional<User>
	 */
	Optional<User> findByUsername(String username);

	User findByJwtCode(String jwt);

	User findFirstByUsername(String username);
}
