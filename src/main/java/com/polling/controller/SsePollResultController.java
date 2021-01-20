package com.polling.controller;

import java.io.IOException;

import com.polling.model.Poll;
import com.polling.service.PollService;
import com.polling.sse.SseEngine;
import com.polling.sse.SsePushNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("sse/")
public class SsePollResultController {

  SsePushNotificationService service;
  SseEngine sseEngine;
  PollService pollService;

  @Autowired
  public SsePollResultController(SsePushNotificationService service, SseEngine sseEngine, PollService pollService) {
    this.service = service;
    this.sseEngine = sseEngine;
    this.pollService = pollService;
  }

  @GetMapping(path = "/{poll_id}")
  public ResponseEntity<SseEmitter> doNotify(@PathVariable("poll_id") String poll_id) throws InterruptedException,  IOException {
    SseEmitter emitter = this.sseEngine.addEmitter(poll_id);
    return new ResponseEntity<>(emitter, HttpStatus.OK);
  }

}
