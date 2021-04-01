package com.chitra.repository.home;

import com.chitra.domain.home.HomeLink;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface HomeLinksRepository extends MongoRepository<HomeLink, String> {

}
