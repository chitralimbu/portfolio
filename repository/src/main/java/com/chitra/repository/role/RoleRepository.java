package com.chitra.repository.role;

import com.chitra.domain.role.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String>{
	Role findByRole(String role);
}
