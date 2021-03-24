package com.chitra.domain.media;

import lombok.*;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection="photos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Photo {

	@Id
	private String id;
	private String title;
	private Binary image;
	private String category;
}
