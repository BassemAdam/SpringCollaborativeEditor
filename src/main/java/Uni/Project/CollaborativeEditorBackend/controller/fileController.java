package Uni.Project.CollaborativeEditorBackend.controller;

import Uni.Project.CollaborativeEditorBackend.model.*;
import Uni.Project.CollaborativeEditorBackend.service.fileService;
import Uni.Project.CollaborativeEditorBackend.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/file")
public class fileController {

    @Autowired
    private fileService service;

    @Autowired
    private userService usrService;

    @GetMapping("/{id}/{userId}")
    public File getFile(@PathVariable String id, @PathVariable String userId) {
        return service.getFile(id, userId);
    }

    @DeleteMapping("/delete/{id}/{userId}")
    public ResponseEntity<String> deleteFile(@PathVariable String id, @PathVariable String userId) {
        return service.deleteFile(id, userId);
    }
    @RequestMapping("/add")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public File addFile(@RequestBody File file) {
        return service.addFile(file);
    }

    @PutMapping("/update/{id}/{userId}")
    public File updateFileName(@PathVariable String id, @PathVariable String userId, @RequestBody UpdateFileNameRequest request) {
        return service.updateFileName(id, request.getFileName(), userId);
    }

    @PostMapping("/shareFile/{ownerId}")
    public ResponseEntity<?> shareFile(@PathVariable String ownerId, @RequestBody ShareFileRequest request) {
        User owner = usrService.findUserById(ownerId);
        if (owner == null) {
            return new ResponseEntity<>("Owner not found", HttpStatus.NOT_FOUND);
        }

        User user = usrService.findUserById(request.getUserId());
        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        // Check if the owner is trying to share the file with themselves
        if (ownerId.equals(request.getUserId())) {
            return new ResponseEntity<>("Cannot share file with yourself", HttpStatus.BAD_REQUEST);
        }

        if (owner.hasFileWithRole(request.getFileId(), UserFile.Role.OWNER)) {
            return new ResponseEntity<>(service.shareFile(request.getUserId(), request.getFileId(), request.getFileName(), request.getRole()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Owner does not have this file", HttpStatus.CONFLICT);
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/createFile")
    public ResponseEntity<?> createFile(@RequestBody CreateFileRequest request) {
        User user = usrService.findUserById(request.getUserId());
        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        File file = new File();
        file.setFileName(request.getFileName());
        file = service.addFile(file); // This should save the file to the database and set the fileID

        UserFile userFile = new UserFile();
        userFile.setFileID(file.getFileID());
        userFile.setFileName(file.getFileName());
        userFile.setRole(UserFile.Role.OWNER);

        user.getFiles().add(userFile);
        usrService.saveUser(user); // This should save the user to the database

        return new ResponseEntity<>(file, HttpStatus.CREATED);
    }


    @CrossOrigin(origins = "http://localhost:3000")
    @PatchMapping("/saveEdits/{id}/{userId}")
    public ResponseEntity<?> saveEdits(@RequestBody SaveEditRequest request, @PathVariable String id, @PathVariable String userId)
    {
        User user = usrService.findUserById(userId);
        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        service.findFileAndUpdate(id,request.getContent());
        File updatedFile = service.findFileById(id);
        return new ResponseEntity<>(updatedFile, HttpStatus.ACCEPTED);
    }


    @DeleteMapping("/ashraf/{id}/{userId}")
    public ResponseEntity<User> AsshrafAPi(@PathVariable String id, @PathVariable String userId) {
        try {
            User user = service.ashraoufapiwanted(userId, id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            // Log the exception
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
