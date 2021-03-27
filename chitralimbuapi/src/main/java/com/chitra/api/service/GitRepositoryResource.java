package com.chitra.api.service;


import com.chitra.api.git.GitApiController;
import com.chitra.domain.git.GitRepository;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Getter
public class GitRepositoryResource extends ResourceSupport {

    private final GitRepository gitRepository;

    public GitRepositoryResource(final GitRepository gitRepository){
        this.gitRepository = gitRepository;
        final String name = gitRepository.getName();
        add(linkTo(GitApiController.class).slash(name).withSelfRel());
        add(linkTo(GitApiController.class).withRel("all"));
    }
}
