package Uni.Project.CollaborativeEditorBackend.model;

// ShareFileRequest.java

import lombok.Data;

@Data
public class ShareFileRequest {
    private String username;
    private String fileId;
//    private String fileName;
    private UserFile.Role role;

}