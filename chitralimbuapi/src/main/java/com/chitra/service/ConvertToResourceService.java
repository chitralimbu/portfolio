package com.chitra.service;

import com.chitra.domain.git.GitRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConvertToResourceService {

    public List<GitRepositoryResource> convertToGitRepoResourceList(List<GitRepository> allRepo){
        return allRepo.stream()
                .map(GitRepositoryResource::new)
                .collect(Collectors.toList());
    }

    public GitRepositoryResource convertToGitRepoResource(GitRepository gitRepository){
        return new GitRepositoryResource(gitRepository);
    }
}
