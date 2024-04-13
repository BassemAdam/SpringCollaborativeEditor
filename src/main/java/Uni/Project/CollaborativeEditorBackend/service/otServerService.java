package Uni.Project.CollaborativeEditorBackend.service;

import Uni.Project.CollaborativeEditorBackend.model.File;
import Uni.Project.CollaborativeEditorBackend.model.otServer;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.util.*;

//todo: THIS IS A WORK IN PROGRESS
@Service
public class otServerService {

    private List<otServer> servers = new ArrayList<otServer>();

    private int createOTServer(int port, String id){
        otServer server = new otServer(port, id);
        servers.add(server);
        return server.getServer().getLocalPort();
    }

    private int findOTServer(String id){
        Optional<otServer> foundItem = servers.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst();

        if (foundItem.isPresent())
            return foundItem.get().getServer().getLocalPort();
        else
            return -1;
    }

    public int connect(String id){

        int port = findOTServer(id);
        if (port == -1)
            return createOTServer(0,id);
        return port;
    }
}
