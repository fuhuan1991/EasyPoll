package com.polling.service;

import com.polling.Uuid64;
import com.polling.model.Option;
import com.polling.model.Poll;
import com.polling.model.Question;
import com.polling.repository.OptionRepository;
import com.polling.repository.PollRepository;
import com.polling.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PollService {
  private final PollRepository pollRepository;
  private final QuestionService questionService;

  @Autowired
  PollService(PollRepository pollRepository, QuestionService questionService) {
    this.pollRepository = pollRepository;
    this.questionService = questionService;
  }

  public void insertPoll(Poll poll) {
    if (poll.getId() == null) {
      String uuid = UUID.randomUUID().toString();
      String id = Uuid64.uuidHexToUuid64(uuid);
      poll.setId(id);
    }
    this.pollRepository.insertPoll(poll);
    String poll_id = poll.getId();
    for (Question o : poll.getQuestions()) {
      o.setPoll_id(poll_id);
      this.questionService.insertQuestion(o);
    }
  }

  public List<Poll> findAllPolls() {
    List<Poll> polls =  pollRepository.findAllPolls();
    for (Poll target : polls) {
      List<Question> questions = this.questionService.findAllPollQuestions(target.getId());
      target.setQuestions(questions);
    }
    return polls;
  }

  public Poll findPollById(String id) {
    Poll target = pollRepository.findPollById(id);
    List<Question> list = this.questionService.findAllPollQuestions(id);
    target.setQuestions(list);
    return target;
  }

//  public void updatePoll(Poll poll) {
//    pollRepository.updatePoll(poll);
//  }

  public void deletePoll(String id) {
    Poll poll = pollRepository.findPollById(id);
    this.deletePoll(poll);
  }

  public void deletePoll(Poll poll) {
    for (Question qustion : poll.getQuestions()) {
      this.questionService.deleteQuestion(qustion);
    }
    pollRepository.deletePoll(poll.getId());
  }

}
