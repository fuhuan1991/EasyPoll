package com.polling.event;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
class VoteEventListener {

  @Async
  @EventListener
  void handleUserRemovedEvent(VoteEvent event) {
    System.out.println(event);
  }

}