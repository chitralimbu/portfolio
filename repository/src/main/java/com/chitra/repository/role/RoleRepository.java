package com.chitra.repository.role;

import com.chitra.domain.role.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface RoleRepository extends MongoRepository<Role, String>{
	Role findByRole(String role);
	List<Role> findAllByRoleContaining(String role);
}
