package Uni.Project.CollaborativeEditorBackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFile {


    //private File file;
    private String fileID;
    private String fileName;
    private Role role;



    public enum Role {
        OWNER,
        VIEWER,
        EDITOR
    }
}