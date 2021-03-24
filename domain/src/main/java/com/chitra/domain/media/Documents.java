package com.chitra.domain.media;

import lombok.*;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="document")
public class Documents {
	
	@Id
	private String id;
	private String title;
	private Binary doc;
	private long count = 0;
}
