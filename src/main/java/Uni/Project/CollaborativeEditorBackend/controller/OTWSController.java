package Uni.Project.CollaborativeEditorBackend.controller;

import Uni.Project.CollaborativeEditorBackend.model.OTMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class OTWSController {

    @MessageMapping("/OT.sendMessage")
    @SendTo("/OTBroker/broadcast")
    public OTMessage sendOTMessage(@Payload OTMessage otMessage) {
        return otMessage;
    }

    @MessageMapping("/OT.insert")
    @SendTo("/OTBroker/broadcast")
    public OTMessage insertChar(@Payload OTMessage otMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("userID", otMessage.getUserID());
        headerAccessor.getSessionAttributes().put("version", otMessage.getVersion());
        headerAccessor.getSessionAttributes().put("operation", otMessage.getOperation());
        headerAccessor.getSessionAttributes().put("value", otMessage.getValue());
        headerAccessor.getSessionAttributes().put("index", otMessage.getIndex());
        return otMessage;
    }
}
