package Uni.Project.CollaborativeEditorBackend.controller;

import Uni.Project.CollaborativeEditorBackend.model.File;
import Uni.Project.CollaborativeEditorBackend.model.ShareFileRequest;
import Uni.Project.CollaborativeEditorBackend.model.UpdateFileNameRequest;
import Uni.Project.CollaborativeEditorBackend.model.User;
import Uni.Project.CollaborativeEditorBackend.service.fileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/file")
public class fileController {

    @Autowired
    private fileService service;

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
    public User shareFile(@RequestBody ShareFileRequest request) {
        return service.shareFile(request.getUserId(), request.getFileId(), request.getRole());
    }
}
