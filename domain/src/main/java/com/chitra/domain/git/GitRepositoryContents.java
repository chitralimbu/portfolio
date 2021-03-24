package com.chitra.domain.git;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@Getter
@Setter
@Document(collection="gitrepositorycontents")
public class GitRepositoryContents {
	@Id
	@NonNull
	private String sha;
	@NonNull
	private String name;
	@NonNull
	private String path;
	@NonNull
	private String url;
	@NonNull
	private String html_url;
	@NonNull
	private String git_url;
	@NonNull
	private String type;
	@NonNull
	private String download_url = "none";
	
	private boolean ignore = false;
	
	@DBRef
	private List<GitRepositoryRecursive> recursive;
	
	private String raw;
	
	public boolean getIgnore() {
		return ignore;
	}
}
