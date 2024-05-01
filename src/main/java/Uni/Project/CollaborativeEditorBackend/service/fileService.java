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

import java.util.List;
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

        // Iterate over all users
        List<User> users = userRepoo.findAll();
        for (User user : users) {
            // Check if the user's files contain the deleted file
            if (user.getFiles().removeIf(userFile -> userFile.getFileID().equals(id))) {
                // If the file was removed, save the updated user
                userRepoo.save(user);
            }
        }

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
        fileRepo.save(file);

        // Iterate over all users
        List<User> users = userRepoo.findAll();
        for (User user : users) {
            // Check if the user's files contain the updated file
            for (UserFile userFile : user.getFiles()) {
                if (userFile.getFileID().equals(id)) {
                    // If the file is found, update the filename
                    userFile.setFileName(newFileName);
                }
            }
            // Save the updated user
            userRepoo.save(user);
        }

        return file;
    } else {
        throw new RuntimeException("File not found with id " + id);
    }
}

  public User shareFile(String userId, String fileId, String fileName, UserFile.Role role) {
    User user = userRepoo.findById(userId).orElseThrow(() -> new RuntimeException("User not found with id " + userId));
    File file = fileRepo.findById(fileId).orElseThrow(() -> new RuntimeException("File not found with id " + fileId));

    // Check if the file is already shared with the user
    Optional<UserFile> optionalUserFile = user.getFiles().stream()
        .filter(userFile -> userFile.getFileID().equals(fileId))
        .findFirst();

    if (optionalUserFile.isPresent()) {
        // If the file is already shared, update the role
        UserFile userFile = optionalUserFile.get();
        userFile.setRole(role);
    } else {
        // If it's the first time the file is being shared, add it to the user's list of files
        UserFile userFile = new UserFile();
        userFile.setFileID(fileId);
        userFile.setFileName(fileName);
        userFile.setRole(role);
        user.getFiles().add(userFile);
    }

    return userRepoo.save(user);
}


}
