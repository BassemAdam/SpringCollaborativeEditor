package Uni.Project.CollaborativeEditorBackend.controller;

import Uni.Project.CollaborativeEditorBackend.model.File;
import Uni.Project.CollaborativeEditorBackend.model.ShareFileRequest;
import Uni.Project.CollaborativeEditorBackend.model.UpdateFileNameRequest;
import Uni.Project.CollaborativeEditorBackend.model.User;
import Uni.Project.CollaborativeEditorBackend.service.fileService;
import Uni.Project.CollaborativeEditorBackend.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/file")
public class fileController {

    @Autowired
    private fileService service;

    @Autowired
    private userService usrService;

    @GetMapping("/{id}")
    public File getFile(@PathVariable String id) {
        return service.findFileById(id);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteFile(@PathVariable String id) {
        return service.deleteFile(id);
    }

    @RequestMapping("/add")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public File addFile(@RequestBody File file) {
        return service.addFile(file);
    }

    @PutMapping("/update/{id}")
    public File updateFileName(@PathVariable String id, @RequestBody UpdateFileNameRequest request) {
        return service.updateFileName(id, request.getFileName());
    }

       @PostMapping("/shareFile")
        public ResponseEntity<?> shareFile(@RequestBody ShareFileRequest request) {
            User user = usrService.findUserById(request.getUserId());
            if (user == null) {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }

            if (user.hasFileWithRole(request.getFileId(), request.getRole())) {
                return new ResponseEntity<>("User already has this file with the given role", HttpStatus.CONFLICT);
            }

           return new ResponseEntity<>(service.shareFile(request.getUserId(), request.getFileId(), request.getFileName(), request.getRole()), HttpStatus.OK);

       }
}
