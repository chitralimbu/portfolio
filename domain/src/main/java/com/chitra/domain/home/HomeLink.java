package com.chitra.domain.home;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "homelinks")
public class HomeLink {
    @Id
    private String id;
    private String title;
    private String url;
    private String imageUrl;
}
