package com.chitra.service.git;

import com.chitra.domain.git.GitRepository;
import com.chitra.domain.git.GitRepositoryContents;
import com.chitra.domain.git.GitRepositoryRecursive;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CodeService {

	public List<String> generateAllHeadings(List<GitRepositoryContents> repoContents){
		List<String> allHeadings = new ArrayList<>();
		for(GitRepositoryContents contents: repoContents) {
			if(contents.getType().equals("dir")) {
				for(GitRepositoryRecursive allRec: contents.getRecursive()) {
					String fullPath = String.format("%s",allRec.getPath());
					allHeadings.add(filterPath(fullPath));
				}
			}else {
				allHeadings.add(contents.getPath());
			}
			
		}
		return allHeadings;
	}
	
	public String filterPath(String fullPath) {
		List<String> fullPathSplit = Arrays.asList(fullPath.split("/"));
		return fullPathSplit.get(fullPathSplit.size() -1);
	}
	
	public List<GitRepositoryContents> turncatePath(List<GitRepositoryContents> gitRepositoryContents){
		for(GitRepositoryContents gitRepoContents: gitRepositoryContents) {
			List<GitRepositoryRecursive> listRecur = gitRepoContents.getRecursive();
			if(listRecur != null) {
				for(GitRepositoryRecursive recur : gitRepoContents.getRecursive()) {
					recur.setPath(filterPath(recur.getPath()));
				}
			}
		}
		return gitRepositoryContents;
	}
	
	public List<String> findURLs(List<GitRepository> allRepositories){
		List<String> allURL = new ArrayList<>();
		
		return allURL;
	}
}
