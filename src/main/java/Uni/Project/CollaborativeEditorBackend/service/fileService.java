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



    public File getFile(String id, String userId) {
        File file = fileRepo.findById(id).orElseThrow(() -> new RuntimeException("File not found with id " + id));

        // Check if the user is authorized to view the file
        User user = userRepoo.findById(userId).orElseThrow(() -> new RuntimeException("User not found with id " + userId));
      if (!user.hasFileWithRole(id, UserFile.Role.EDITOR) && !user.hasFileWithRole(id, UserFile.Role.VIEWER) && !user.hasFileWithRole(id, UserFile.Role.OWNER)) {
    throw new RuntimeException("Unauthorized");
}

        return file;
    }
    public ResponseEntity<String> deleteFile(String id, String userId){
        Optional<File> file = fileRepo.findById(id);
        if (file.isPresent()) {
            // Check if the user is the owner of the file
            User user = userRepoo.findById(userId).orElseThrow(() -> new RuntimeException("User not found with id " + userId));
            if (!user.hasFileWithRole(id, UserFile.Role.OWNER)) {
                return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
            }

            fileRepo.deleteById(id);

            // Iterate over all users
            List<User> users = userRepoo.findAll();
            for (User otherUser : users) {
                // Check if the user's files contain the deleted file
                if (otherUser.getFiles().removeIf(userFile -> userFile.getFileID().equals(id))) {
                    // If the file was removed, save the updated user
                    userRepoo.save(otherUser);
                }
            }

            return new ResponseEntity<>(id + " Deleted Successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
        }
    }

    public File updateFileName(String id, String newFileName, String userId) {
    Optional<File> optionalFile = fileRepo.findById(id);
    if (optionalFile.isPresent()) {
        // Check if the user is authorized to edit the file
        User user = userRepoo.findById(userId).orElseThrow(() -> new RuntimeException("User not found with id " + userId));
        if (!user.hasFileWithRole(id, UserFile.Role.EDITOR) && !user.hasFileWithRole(id, UserFile.Role.OWNER)) {
            throw new RuntimeException("Unauthorized");
        }

        File file = optionalFile.get();
        file.setFileName(newFileName);
        fileRepo.save(file);

        // Iterate over all users
        List<User> users = userRepoo.findAll();
        for (User otherUser : users) {
            // Check if the user's files contain the updated file
            for (UserFile userFile : otherUser.getFiles()) {
                if (userFile.getFileID().equals(id)) {
                    // If the file is found, update the filename
                    userFile.setFileName(newFileName);
                }
            }
            // Save the updated user
            userRepoo.save(otherUser);
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

    public File findFileById(String id) {
        return fileRepo.findById(id).orElse(null);
    }

    public File findFileAndUpdate(String id, Object content){
        File file = findFileById(id);
        file.setFileContent(content);
        return fileRepo.save(file);
    }


    public User ashraoufapiwanted(String userId, String fileId) {
        User user = userRepoo.findById(userId).orElseThrow(() -> new RuntimeException("User not found with id " + userId));

        // Remove the UserFile from the user's list of files
        user.getFiles().removeIf(userFile -> userFile.getFileID().equals(fileId));

        // Save the updated user
        return userRepoo.save(user);
    }
}
