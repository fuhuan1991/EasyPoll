package com.polling.controller;

import com.polling.event.VoteEventPublisher;
import com.polling.model.Option;
import com.polling.model.Sample;
import com.polling.model.Poll;
import com.polling.service.OptionService;
import com.polling.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.UUID;

@RestController
@RequestMapping("api/")
public class PollController {

  private final PollService pollService;
  private final OptionService optionService;
  private final VoteEventPublisher voteEventPublisher;

  @Autowired
  public PollController(PollService pollService, OptionService optionService, VoteEventPublisher voteEventPublisher) {
    this.pollService = pollService;
    this.optionService = optionService;
    this.voteEventPublisher = voteEventPublisher;
  }

  /**
   * Create a new poll. If every thing is alright, a new poll along with all the questions and options will
   * be inserted into the DB. Otherwise, an exception shall be raised.
   */
  @CrossOrigin
  @PostMapping(path = "/create")
  public String createPoll (@RequestBody Poll poll) throws Exception{
    System.out.println("----------------create-------------------");
    poll.show();
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    poll.setStartTime(timestamp.toString());

    try {
      this.pollService.insertPoll(poll);
      if (poll.getId() == null) throw new Exception("creation failed");
      return poll.getId();
    } catch (Exception e) {
      e.printStackTrace();
      throw new Exception(e.getMessage());
    }
  }

  /**
   * Take a poll. If every thing is alright, changes shall be made in DB.
   * Otherwise, an exception shall be raised.
   */
  @CrossOrigin
  @PostMapping(path = "/take_poll/{poll_id}")
  public void takePoll (@RequestBody Sample sample, @PathVariable("poll_id") String poll_id) throws Exception{

    Poll poll = this.pollService.findPollById(poll_id);

    if (poll == null) throw new Exception("The poll does not exist");

    LinkedList<String> optionIds = sample.getOptionIds();
    LinkedList<Option> options = new LinkedList<>();

    for (String id : optionIds) {
      Option target = this.optionService.findOptionById(UUID.fromString(id));
      if (target == null) throw new Exception("invalid ID found, submit failed");
      options.add(target);
    }

    for (Option target : options) {
      this.optionService.inc(target);
    }

    voteEventPublisher.publishEvent(poll_id);
  }


//  public static void main(String[] args) {
//
//  }
}
