package com.polling.controller;

import com.polling.model.Option;
import com.polling.model.Sample;
import com.polling.model.Poll;
import com.polling.service.OptionService;
import com.polling.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.UUID;

@RestController
@RequestMapping("api/")
public class PollController {

  private final PollService pollService;
  private final OptionService optionService;

  @Autowired
  public PollController(PollService pollService, OptionService optionService) {
    this.pollService = pollService;
    this.optionService = optionService;
  }

  /**
   * Create a new poll. If every thing is alright, a new poll along with all the questions and options will
   * be inserted into the DB. Otherwise, an exception shall be raised.
   */
  @PostMapping(path = "/create")
  public String createPoll (@RequestBody Poll poll) throws Exception{
    System.out.println("----------------create-------------------");
    poll.show();

    poll.setStartTime("2045-06-2 15:30:00");
    poll.setEndTime("2045-06-6 15:30:00");

    try {
      this.pollService.insertPoll(poll);
      if (poll.getId() == null) throw new Exception("creation failed");
      return poll.getId();
    } catch (Exception e) {
      throw new Exception("creation failed");
    }
  }

  /**
   * Take a poll. If every thing is alright, changes shall be made in DB.
   * Otherwise, an exception shall be raised.
   */
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
  }
}
