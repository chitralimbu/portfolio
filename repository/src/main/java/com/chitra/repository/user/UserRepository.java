package com.chitra.repository.user;

import com.chitra.domain.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface UserRepository extends MongoRepository<User, Integer> {

	Optional<User> findByUsername(String username);
	
}
