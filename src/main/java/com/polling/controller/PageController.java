package com.polling.controller;

import com.polling.model.Poll;
import com.polling.service.OptionService;
import com.polling.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class PageController {

  private final PollService pollService;
  private final OptionService optionService;

  @Autowired
  public PageController (PollService pollService, OptionService optionService) {
    this.pollService = pollService;
    this.optionService = optionService;
  }


  /**
   * Request of administration page for a certain poll.
   * If this poll does not exist in DB, a error page will be returned.
   * The content of this administration page includes some basic information,
   * and links to share.
   */
  @GetMapping("/admin/{poll_id}")
  public String admin(Model model, @PathVariable("poll_id") String poll_id) {

    Poll poll = this.pollService.findPollById(poll_id);

    if (poll == null) {
      model.addAttribute("message", "The poll does not exist.");
      return "error";
    }

    model.addAttribute("poll_id", poll_id);
    model.addAttribute("poll_title", poll.getTitle());
    model.addAttribute("start_time", poll.getStartTime());
    model.addAttribute("end_time", poll.getEndTime());

    return "admin";
  }

  /**
   * Request of result page for a certain poll.
   * If this poll does not exist in DB, a error page will be returned.
   * The result of this poll shall be displayed in here.
   */
  @GetMapping("/result/{poll_id}")
  public String result(Model model, @PathVariable("poll_id") String poll_id) {

    Poll poll = this.pollService.findPollById(poll_id);

    if (poll == null) {
      model.addAttribute("message", "The poll does not exist.");
      return "error";
    }

    model.addAttribute("poll", poll);

    return "result";
  }

  /**
   * Request of interaction page for a certain poll.
   * If this poll does not exist in DB, a error page will be returned.
   * In this page, a user can take the poll.
   */
  @GetMapping("/poll/{poll_id}")
  public String poll(Model model, @PathVariable("poll_id") String poll_id) {

    Poll poll = this.pollService.findPollById(poll_id);

    if (poll == null) {
      model.addAttribute("message", "The poll does not exist.");
      return "error";
    }

    model.addAttribute("poll", poll);

    return "poll";
  }

  /**
   * Error page
   */
//  @GetMapping("/error")
//  public String error(Model model) {
//    return "error";
//  }

  /**
   * This is a temporary test api for getting all poll information
   */
  @GetMapping("/special/getAll")
  public String getAll(Model model, ) {

    List<Poll> polls = this.pollService.findAllPolls();

    model.addAttribute("polls", polls);

    return "getAll";
  }

}