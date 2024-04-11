package Uni.Project.CollaborativeEditorBackend.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private List<UserFile> files = new ArrayList<>();
    // even if no files have been added to the user, getFiles() will return an empty list instead of null
    //so i have added just to avoid errors
    @Id
    private String userID;
    private String username;
    private String password;
    private String email;
    //private List<UserFile> files;
}
