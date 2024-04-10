package Uni.Project.CollaborativeEditorBackend.service;

import Uni.Project.CollaborativeEditorBackend.model.File;
import Uni.Project.CollaborativeEditorBackend.repository.fileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class fileService {

    @Autowired
    private fileRepository fileRepo;

    public File addFile(File doc){
        return fileRepo.save(doc);
    }

    public File findFileById(String id){
        return fileRepo.findById(id).orElse(null);
    }

    public String deleteFile(String id){
        fileRepo.deleteById(id);
        return id +" Deleted Successfully!";
    }
}
