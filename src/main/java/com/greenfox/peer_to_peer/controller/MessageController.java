package com.greenfox.peer_to_peer.controller;

import com.greenfox.peer_to_peer.model.DTO;
import com.greenfox.peer_to_peer.model.Status;
import com.greenfox.peer_to_peer.repository.MessageRepository;
import com.greenfox.peer_to_peer.service.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class MessageController {

  @Autowired
  MessageRepository messageRepository;

  @CrossOrigin("*")
  @PostMapping("/api/message/receive")
  public Status receiveNewMessage(@RequestBody DTO dto) {
    messageRepository.save(dto.getMessage());
    RestTemplate restTemplate = new RestTemplate();
    dto.setClient(new User(System.getenv("CHAT_APP_UNIQUE_ID")));
    restTemplate.postForObject(System.getenv("CHAT_APP_PEER_ADDRESS")+ "/api/message/receive", dto, Status.class);
    System.out.println("message sent");
    return new Status("ok");
  }
}