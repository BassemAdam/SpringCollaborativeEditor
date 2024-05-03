package Uni.Project.CollaborativeEditorBackend.model;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

//todo: THIS IS A WORK IN PROGRESS
@Setter
@Getter
public class otServer {

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

    public boolean connectSockets(){
        try {
            if (clientCount < clients.length){
                clients[clientCount] = server.accept();

                new otServerThread(clients[clientCount], clientCount).start();
                clientCount++;
                System.out.println("Connected to OTServer with id: " + id);
                System.out.println("Port: " + clients[clientCount - 1].getLocalPort() + " Address: " + clients[clientCount - 1].getLocalAddress());
                return true;
            }
            else {
                System.out.println("No client connected: limit exceeded");
                return false;
            }
        } catch (IOException e) {
            System.out.println("Error connecting to " + id);
            e.printStackTrace();
            return false;
        }
    }
}
