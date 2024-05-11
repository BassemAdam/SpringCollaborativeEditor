package Uni.Project.CollaborativeEditorBackend.repository;

import Uni.Project.CollaborativeEditorBackend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface userRepository extends MongoRepository<User, String>{


    User findByUsername(String username);

    User findByEmail(String email);

    List<User> findByFilesFileID(String fileId);
}
