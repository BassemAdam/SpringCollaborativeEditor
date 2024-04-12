package Uni.Project.CollaborativeEditorBackend.service;

import Uni.Project.CollaborativeEditorBackend.model.File;
import Uni.Project.CollaborativeEditorBackend.model.User;
import Uni.Project.CollaborativeEditorBackend.model.UserFile;
import Uni.Project.CollaborativeEditorBackend.repository.fileRepository;
import Uni.Project.CollaborativeEditorBackend.repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class fileService {

    @Autowired
    private userRepository userRepoo;
    @Autowired
    private fileRepository fileRepo;

    public File addFile(File doc){
        return fileRepo.save(doc);
    }

    public File findFileById(String id){
        return fileRepo.findById(id).orElse(null);
    }

    public ResponseEntity<String> deleteFile(String id){
        Optional<File> file = fileRepo.findById(id);
        if (file.isPresent()) {
            fileRepo.deleteById(id);
            return new ResponseEntity<>(id + " Deleted Successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
    }
    public File updateFileName(String id, String newFileName) {
        Optional<File> optionalFile = fileRepo.findById(id);
        if (optionalFile.isPresent()) {
            File file = optionalFile.get();
            file.setFileName(newFileName);
            return fileRepo.save(file);
        } else {
            throw new RuntimeException("File not found with id " + id);
        }
    }

   public User shareFile(String userId, String fileId, String fileName, UserFile.Role role) {
    User user = userRepoo.findById(userId).orElseThrow(() -> new RuntimeException("User not found with id " + userId));
    File file = fileRepo.findById(fileId).orElseThrow(() -> new RuntimeException("File not found with id " + fileId));

    UserFile userFile = new UserFile();
    userFile.setFileID(fileId);
    userFile.setFileName(fileName); // set the fileName
    userFile.setRole(role);

    user.getFiles().add(userFile);
    return userRepoo.save(user);
}


}
