package com.chitra.domain.git;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
@Document(collection="gitrepositoryrecursive")
public class GitRepositoryRecursive {
	@Id
	private String id;
	@NonNull
	private String path;
	@NonNull
	private String type;
	@NonNull
	private String sha;
	@NonNull
	private String url;
	private String raw;
	private String code = "no code";
}
