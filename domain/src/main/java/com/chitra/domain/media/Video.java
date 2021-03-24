package com.chitra.domain.media;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;

@Data
@Getter
@Setter
public class Video {
	private String title;
	private InputStream stream;
}
