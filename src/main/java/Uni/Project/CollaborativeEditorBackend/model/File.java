package Uni.Project.CollaborativeEditorBackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Document(collection = "files")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class File {

    @Id
    private String FileID;
    private String fileName;

    //add raw data here

    private String fileContent;
}
