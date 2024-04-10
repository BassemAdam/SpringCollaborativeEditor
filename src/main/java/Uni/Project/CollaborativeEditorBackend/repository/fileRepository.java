package Uni.Project.CollaborativeEditorBackend.repository;

import Uni.Project.CollaborativeEditorBackend.model.File;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface fileRepository extends MongoRepository<File, String>{
}
