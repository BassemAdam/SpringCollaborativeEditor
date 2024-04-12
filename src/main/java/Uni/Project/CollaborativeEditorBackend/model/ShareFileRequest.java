package Uni.Project.CollaborativeEditorBackend.model;

// ShareFileRequest.java

import lombok.Data;

@Data
public class ShareFileRequest {
    private String userId;
    private String fileId;
    private String fileName;
    private UserFile.Role role;

}