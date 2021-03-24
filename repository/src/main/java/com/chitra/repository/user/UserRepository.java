package com.chitra.repository.user;

import com.chitra.domain.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, Integer> {

	User findByUsername(String username);
	
}
