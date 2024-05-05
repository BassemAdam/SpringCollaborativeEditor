package Uni.Project.CollaborativeEditorBackend.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OTMessage {
    String userID;
    int version;
    messageType operation;
    char value;
    int index;
}
enum messageType{
    insert,
    delete,
    // TODO - CHECK IF "JOIN" & "LEAVE" ARE NEEDED
}