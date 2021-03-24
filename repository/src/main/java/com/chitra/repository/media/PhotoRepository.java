package com.chitra.repository.media;

import com.chitra.domain.media.Photo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PhotoRepository extends MongoRepository<Photo, String>{
	Photo findByTitle(String title);
	void deleteByTitle(String title);
}
