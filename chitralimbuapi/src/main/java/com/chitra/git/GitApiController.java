package com.chitra.git;


import com.chitra.service.ConvertToResourceService;
import com.chitra.service.GitRepositoryResource;
import com.chitra.service.git.GitRepositoryService;
import com.chitra.domain.git.GitRepository;
import com.chitra.repository.git.GitRepositoryRepository;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/git")
public class GitApiController {

	@GetMapping("/test")
	public ResponseEntity<String> test(){
		return new ResponseEntity<>("{ message: hello }", HttpStatus.OK);
	}

	private final GitRepositoryService gitRepoService;
	private final GitRepositoryRepository gitRepo;
	private final ConvertToResourceService convertService;

	public GitApiController(GitRepositoryService gitRepoService, GitRepositoryRepository gitRepo, ConvertToResourceService convertService) {
		this.gitRepoService = gitRepoService;
		this.gitRepo = gitRepo;
		this.convertService = convertService;
	}

	@GetMapping(value = "/hal/full-refresh", produces = "application/hal+json")
	public ResponseEntity<List<GitRepositoryResource>> fullRefresh(Gson gson){
		List<GitRepository> allRepositories = gitRepoService
						.generateFinalGitRepository(gitRepoService.fullRefreshGitRepository());
		return new ResponseEntity<>(convertService.convertToGitRepoResourceList(allRepositories), HttpStatus.OK);
	}

	@GetMapping(produces = "application/hal+json")
	public ResponseEntity<List<GitRepositoryResource>> allRepository(){
		return new ResponseEntity<>(convertService.convertToGitRepoResourceList(gitRepo.findAll()), HttpStatus.OK);
	}

	@GetMapping(value = "/hal/{repository}", produces = "application/hal+json")
	public ResponseEntity<GitRepositoryResource> getRepository(@PathVariable("repository") String repository) {
		return new ResponseEntity<>(convertService.convertToGitRepoResource(gitRepo.findByName(repository)), HttpStatus.OK);
	}

	@GetMapping(value = "/hal/new/{repository}", produces = "application/hal+json")
	public ResponseEntity<GitRepositoryResource> addNewRepository(@PathVariable("repository") String repository){
		return new ResponseEntity<>(convertService.convertToGitRepoResource(gitRepoService.updateRepository(repository)), HttpStatus.CREATED);
	}

	@GetMapping(value = "/hal/refresh/{repository}", produces = "application/hal+json")
	public ResponseEntity<GitRepositoryResource> updateRepository(@PathVariable("repository") String repository) {
		return new ResponseEntity<>(convertService.convertToGitRepoResource(gitRepoService.updateRepository(repository)), HttpStatus.OK);
	}
}
