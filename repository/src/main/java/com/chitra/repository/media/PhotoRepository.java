package com.chitra.repository.media;

import com.chitra.domain.media.Photo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PhotoRepository extends MongoRepository<Photo, String>{
	Photo findByTitle(String title);
	void deleteByTitle(String title);
}
