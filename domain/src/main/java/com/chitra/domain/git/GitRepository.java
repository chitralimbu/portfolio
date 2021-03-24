package com.chitra.domain.git;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
@Document(collection="gitrepository")
public class GitRepository {
	@Id
	@NonNull
	private String id;
	@NonNull
	private String name;
	@NonNull
	private String full_name;
	@NonNull
	private String html_url;
	@NonNull
	private String description;
	
	private boolean igonre = false;

	@DBRef
	private List<GitRepositoryContents> allContents;
}

