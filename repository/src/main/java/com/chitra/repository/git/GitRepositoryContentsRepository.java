package com.chitra.repository.git;


import com.chitra.domain.git.GitRepositoryContents;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GitRepositoryContentsRepository extends MongoRepository<GitRepositoryContents, String>{

}
