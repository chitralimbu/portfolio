package com.chitra.repository.media;

import com.chitra.domain.media.Documents;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DocumentRepository extends MongoRepository<Documents, String>{
	Documents findByTitle(String title);
	void deleteByTitle(String title);
}
