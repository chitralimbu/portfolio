package com.chitra.repository.git;

import com.chitra.domain.git.GitRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface GitRepositoryRepository extends MongoRepository<GitRepository, String>{
	GitRepository findByName(String name);
	Page<GitRepository> findAll(Pageable pageable);
	List<GitRepository> findByNameContainingIgnoreCase(String name);
	List<GitRepository> findByDescriptionContainingIgnoreCase(String name);
}
