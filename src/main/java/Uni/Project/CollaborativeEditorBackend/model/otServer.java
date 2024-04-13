package Uni.Project.CollaborativeEditorBackend.model;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

//todo: THIS IS A WORK IN PROGRESS
@Setter
@Getter
public class otServer extends Thread {

    private String id;
    private File file;
    private ServerSocket server;
    private Socket[] clients = new Socket[6];
    private int clientCount = 0;
    public otServer(int port,String ID){
        try {
            server = new ServerSocket(port);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        id = ID;
        //todo: get file here
    }

    public void connectTo(){
        try {
            clients[clientCount++] = server.accept();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
