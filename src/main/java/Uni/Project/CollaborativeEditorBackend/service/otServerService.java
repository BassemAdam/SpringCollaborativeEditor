package Uni.Project.CollaborativeEditorBackend.service;

import Uni.Project.CollaborativeEditorBackend.model.File;
import Uni.Project.CollaborativeEditorBackend.model.otServer;
import Uni.Project.CollaborativeEditorBackend.repository.fileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

//todo: THIS IS A WORK IN PROGRESS
@Service
public class otServerService {

    @Autowired
    private fileRepository fileRepo;

    public File findFileById(String id) {
        return fileRepo.findById(id).orElse(null);
    }

    private List<otServer> servers = new ArrayList<otServer>();

    private int createOTServer(int port, String id) {
        otServer server = new otServer(port, id);
        // TODO: ASK IF ID OF SERVER IS SAME AS FILE ID, UNCOMMENT CODE
        /*server.setFile(findFileById(id));*/
        servers.add(server);
        System.out.println("Server created on address: " + server.getServer().getInetAddress() + " on port: " + server.getServer().getLocalPort());
        return server.getServer().getLocalPort();
    }

    private int findOTServer(String id) {
        Optional<otServer> foundItem = servers.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst();

        if (foundItem.isPresent())
            return foundItem.get().getServer().getLocalPort();
        else
            return -1;
    }

    public int connect(String id) {
        int port = findOTServer(id);
        if (port == -1) {
            System.out.println("OTServer not found, will attempt creation of OT Server");
            return createOTServer(0, id);
        }
        System.out.println("Connecting to OTServer " + id);
        return port;
    }

    // Connects a socket from client to server
    // returns true if successful connection, false if failed
    public boolean connectSockets(String id) {
        Optional<otServer> chosenServer = servers.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst();
        boolean otServerResponse = false;
        if (chosenServer.isPresent()) {
            otServerResponse = chosenServer.get().connectSockets();
            return otServerResponse;
        } else {
            return false;
        }
    }

}
