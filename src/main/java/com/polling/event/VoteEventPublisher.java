package com.polling.event;

import com.polling.model.Sample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class VoteEventPublisher {

  private final ApplicationEventPublisher publisher;

  @Autowired
  VoteEventPublisher(ApplicationEventPublisher publisher) {
    this.publisher = publisher;
  }

  public void publishEvent(final String poll_id) {
    publisher.publishEvent(new VoteEvent(poll_id));
  }
}