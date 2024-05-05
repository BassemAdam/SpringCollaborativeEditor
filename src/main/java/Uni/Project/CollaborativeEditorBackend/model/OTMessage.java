package Uni.Project.CollaborativeEditorBackend.model;

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
}