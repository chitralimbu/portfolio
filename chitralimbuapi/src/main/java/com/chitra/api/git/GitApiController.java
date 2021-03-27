package com.chitra.api.git;


import com.chitra.domain.git.GitRepository;
import com.chitra.repository.git.GitRepositoryRepository;
import com.chitra.service.git.GitRepositoryService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/git")
public class GitApiController {

	private final GitRepositoryService gitRepoService;
	private final GitRepositoryRepository gitRepo;

	public GitApiController(GitRepositoryService gitRepoService, GitRepositoryRepository gitRepo) {
		this.gitRepoService = gitRepoService;
		this.gitRepo = gitRepo;
	}

	@PostMapping(value = "/full-refresh", produces = "application/hal+json")
	public ResponseEntity<List<GitRepository>> fullRefresh(){
		List<GitRepository> allRepositories = gitRepoService
						.generateFinalGitRepository(gitRepoService.fullRefreshGitRepository());
		return new ResponseEntity<>(allRepositories, HttpStatus.OK);
	}

	@GetMapping(produces = "application/hal+json")
	public ResponseEntity<List<GitRepository>> allRepository(){
		return new ResponseEntity<>(gitRepo.findAll(), HttpStatus.OK);
	}

	@GetMapping(value = "/{repository}", produces = "application/hal+json")
	public ResponseEntity<GitRepository> getRepository(@PathVariable("repository") String repository) {
		return new ResponseEntity<>(gitRepo.findByName(repository), HttpStatus.OK);
	}

	@PostMapping(value = "/new/{repository}", produces = "application/hal+json")
	public ResponseEntity<GitRepository> addNewRepository(@PathVariable("repository") String repository){
		return new ResponseEntity<>(gitRepoService.updateRepository(repository), HttpStatus.CREATED);
	}

	@PutMapping(value = "/refresh/{repository}", produces = "application/hal+json")
	public ResponseEntity<GitRepository> updateRepository(@PathVariable("repository") String repository) {
		return new ResponseEntity<>(gitRepoService.updateRepository(repository), HttpStatus.OK);
	}
}
