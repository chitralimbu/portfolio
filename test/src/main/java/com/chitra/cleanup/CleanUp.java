package com.chitra.cleanup;

import com.chitra.domain.git.GitRepository;
import com.chitra.repository.git.GitRepositoryContentsRepository;
import com.chitra.repository.git.GitRepositoryRecursiveRepository;
import com.chitra.repository.git.GitRepositoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CleanUp {

    private final GitRepositoryRepository gitRepository;
    private final GitRepositoryContentsRepository gitContentsRepository;
    private final GitRepositoryRecursiveRepository gitRecursiveRepository;

    public CleanUp(GitRepositoryRepository gitRepository, GitRepositoryContentsRepository gitContentsRepository, GitRepositoryRecursiveRepository gitRecursiveRepository) {
        this.gitRepository = gitRepository;
        this.gitContentsRepository = gitContentsRepository;
        this.gitRecursiveRepository = gitRecursiveRepository;
    }

    public void deleteRepo(GitRepository repo){
        repo.getAllContents().forEach(content -> {
            if(content.getRecursive() != null){
                content.getRecursive().forEach(recursive -> {
                    //delete all recursive
                    gitRecursiveRepository.delete(recursive);
                });
            }
            //delete all contents
            gitContentsRepository.delete(content);
        });
        //delete all repository
        gitRepository.delete(repo);
    }
}
