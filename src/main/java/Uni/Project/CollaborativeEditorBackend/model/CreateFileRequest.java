package Uni.Project.CollaborativeEditorBackend.model;

import lombok.Data;

@Data
public class CreateFileRequest {
    private String userId;
    private String fileName;
}