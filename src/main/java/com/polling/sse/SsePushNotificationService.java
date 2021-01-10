package com.polling.sse;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.polling.event.VoteEvent;
import com.polling.model.Poll;
import com.polling.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@EnableScheduling
public class SsePushNotificationService {

  private SseEngine sseEngine;
  private PollService pollService;

  @Autowired
  public SsePushNotificationService(SseEngine sseEngine, PollService pollService) {
    this.sseEngine = sseEngine;
    this.pollService = pollService;
  }

  @Async
  @EventListener
  public void doNotify(VoteEvent event) throws IOException {
    System.out.println("--doNotify");
    String poll_id = event.getPoll_id();
    Poll poll = this.pollService.findPollById(poll_id);
    this.sseEngine.dispatchByPollId(poll_id, poll);
  }

}

