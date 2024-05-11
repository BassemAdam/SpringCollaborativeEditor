package Uni.Project.CollaborativeEditorBackend.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    //Bassem there is something that is better to be done from my pov is that we make the name
    //of the files stored in UserFile also so that when we fetch in frontend Allfiles display name
    //and avoid fetching the file itself to save time and resources
    //and when user click on file only then we fetch the file itself
    private List<UserFile> files = new ArrayList<>();
    // even if no files have been added to the user, getFiles() will return an empty list instead of null
    //so i have added just to avoid errors
    @Id
    private String userID;
    private String username;
    private String password;
    private String email;


    public boolean hasFileWithRole(String fileId, UserFile.Role role) {
    return this.getFiles().stream()
        .anyMatch(userFile -> userFile.getFileID().equals(fileId) && userFile.getRole() == role);
}
}
