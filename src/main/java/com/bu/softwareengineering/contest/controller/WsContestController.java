package com.bu.softwareengineering.contest.controller;


import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class WsContestController {

    @MessageMapping("/contest.getNewUser")
    //@SendTo("/topic/public")
    public Map addUser(@Payload Map chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        //headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }
}
