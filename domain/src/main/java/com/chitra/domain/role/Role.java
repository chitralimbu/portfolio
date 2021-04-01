package com.chitra.domain.role;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Getter
@Setter
@NoArgsConstructor
@Document(collection="role")
public class Role {
	
	@Id
	private String id;

	@Indexed(unique = true, direction = IndexDirection.DESCENDING)
	private String role;

	public Role(String role) {
		this.role = role;
	}

}
