package com.chitra.repository.user;

import com.chitra.domain.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface UserRepository extends MongoRepository<User, String> {
	Optional<User> findByUsername(String username);
	Optional<List<User>> findByUsernameContaining(String wildcard);
	Optional<List<User>> findAllByRolesId(String roleId);
}
