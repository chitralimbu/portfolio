package com.chitra.service.media;

import com.chitra.repository.media.DocumentRepository;
import com.chitra.domain.media.Documents;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class DocumentService {
	
	private final DocumentRepository docRepo;

	public DocumentService(DocumentRepository docRepo) {
		this.docRepo = docRepo;
	}

	public void addDocument(String title, MultipartFile file) throws IOException{
		Documents doc = new Documents();
		doc.setTitle(title);
		doc.setDoc(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
		docRepo.insert(doc);
	}

	public List<Documents> getAllDocuments(){
		return docRepo.findAll();
	}

	public void updateDocument(Documents document){
		docRepo.save(document);
	}

	public Documents getDocByTitle(String title) {
		return docRepo.findByTitle(title);
	}
	
	public Documents getDocById(String id) {
		return docRepo.findById(id).get();
	}

	public void deleteDocByTitle(String title){
		docRepo.deleteByTitle(title);
	}
	
}
