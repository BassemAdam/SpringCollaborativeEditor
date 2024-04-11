package Uni.Project.CollaborativeEditorBackend.repository;

import Uni.Project.CollaborativeEditorBackend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface userRepository extends MongoRepository<User, String>{


    User findByUsername(String username);
}
