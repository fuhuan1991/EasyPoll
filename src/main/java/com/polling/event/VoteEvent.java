package com.polling.event;

import com.polling.model.Sample;
import org.springframework.context.ApplicationEvent;

public class VoteEvent extends ApplicationEvent {
  private String poll_id;

  VoteEvent(String poll_id) {
    super(poll_id);
    this.poll_id = poll_id;
  }

  public String getPoll_id() {
    return poll_id;
  }

  public void setPoll_id(String poll_id) {
    this.poll_id = poll_id;
  }

  @Override
  public String toString() {
    return "VoteEvent: poll id is " + this.poll_id;
  }
}
