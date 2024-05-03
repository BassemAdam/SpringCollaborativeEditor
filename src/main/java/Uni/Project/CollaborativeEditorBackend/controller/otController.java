package Uni.Project.CollaborativeEditorBackend.controller;

import Uni.Project.CollaborativeEditorBackend.model.File;
import Uni.Project.CollaborativeEditorBackend.service.otServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;

//todo: THIS IS A WORK IN PROGRESS
@RestController
@RequestMapping("/connect")
public class otController {

    @Autowired
    otServerService service;

    @GetMapping("/{id}")
    public int connect(@PathVariable String id){
        return service.connect(id);
    }

    // Request to connect a socket from client to server
    // returns true if successful connection, false if failed
    @PostMapping("/{id}/connectSocket")
    public boolean connectSocket (@PathVariable String id){ return service.connectSockets(id);}
}
