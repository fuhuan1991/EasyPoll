package com.polling.service;

import com.polling.model.Option;
import com.polling.model.Question;
import com.polling.repository.OptionRepository;
import com.polling.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class QuestionService {
  private final QuestionRepository questionRepository;
  private final OptionService optionService;

  @Autowired
  QuestionService(QuestionRepository questionRepository, OptionService optionService) {
    this.questionRepository = questionRepository;
    this.optionService = optionService;
  }

  public void insertQuestion(Question question) {
    if (question.getId() == null) {
      UUID id = UUID.randomUUID();
      question.setId(id);
    }
    questionRepository.insertQuestion(question);
    UUID question_id = question.getId();
    for (Option o : question.getOptions()) {
      o.setQuestion_id(question_id);
      this.optionService.insertOption(o);
    }
  }

  public List<Question> findAllPollQuestions(String poll_id) {
    List<Question> questions = questionRepository.findAllPollQuestions(poll_id);
    for (Question target : questions) {
      List<Option> options = this.optionService.findAllQuestionOptions(target.getId());
      target.setOptions(options);
    }
    return questions;
  }

  public Question findQuestionById(UUID question_id) {
    Question target = questionRepository.findQuestionById(question_id);
    List<Option> list = this.optionService.findAllQuestionOptions(question_id);
    target.setOptions(list);
    return target;
  }

//  public void updateQuestion(Question question) {
//    questionRepository.updateQuestion(question);
//  }

  public void deleteQuestion(UUID question_id) {
    Question question = this.questionRepository.findQuestionById(question_id);
    this.deleteQuestion(question);
  }

  public void deleteQuestion(Question question) {
    for (Option option : question.getOptions()) {
      this.optionService.deleteOption(option.getId());
    }
    this.questionRepository.deleteQuestion(question.getId());
  }
}
