package com.polling;

import com.polling.model.Option;
import com.polling.model.Poll;
import com.polling.service.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/")
public class PollController {

  private final OptionService optionService;

  @Autowired
  public PollController(OptionService optionService) {
    this.optionService = optionService;
  }

  // create a new poll
  @PostMapping(path = "create")
  public void createPoll (@RequestBody Poll poll) throws Exception{
    System.out.println("----------------create-------------------");


//    ObjectMapper objectMapper = new ObjectMapper();
//    objectMapper.readValue(poll, Poll.class);
  }

  // create a new note
  @PostMapping(path = "t1")
  public void t1 (@RequestBody @Valid Option op) throws Exception{
    System.out.println("----------------create-------------------");
    System.out.println(op);










  }


}
