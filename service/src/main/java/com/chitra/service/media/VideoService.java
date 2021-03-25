package com.chitra.service.media;

import com.chitra.domain.media.Video;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
public class VideoService {

	private final GridFsTemplate gridFsTemplate;
	private final GridFsOperations operations;

	public VideoService(GridFsTemplate gridFsTemplate, GridFsOperations operations) {
		this.gridFsTemplate = gridFsTemplate;
		this.operations = operations;
	}

	public String addVideo(String title, MultipartFile file) throws IOException { 
		DBObject metaData = new BasicDBObject(); 
		metaData.put("type", "video"); 
		metaData.put("title", title); 
		ObjectId id = gridFsTemplate.store(
				file.getInputStream(), file.getName(), file.getContentType(), metaData); 
		log.info(String.format("Successfully uploaded video with title %s", title));
		return id.toString();
	}

	public Video getVideo(String id) throws IllegalStateException, IOException {
		GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id))); 
		Video video = new Video(); 
		video.setTitle(file.getMetadata().get("title").toString()); 
		video.setStream(operations.getResource(file).getInputStream());
		return video; 
	}
}
