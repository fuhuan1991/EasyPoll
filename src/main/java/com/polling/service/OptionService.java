package com.polling.service;

import com.polling.model.Option;
import com.polling.repository.OptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OptionService {
  private final OptionRepository optionRepository;

  @Autowired
  OptionService(OptionRepository optionRepository) {
    this.optionRepository = optionRepository;
  }

  public void insertOption(Option option) {
    if (option.getId() == null) {
      UUID id = UUID.randomUUID();
      option.setId(id);
    }
    int r = optionRepository.insertOption(option);
  }

  public List<Option> findAllQuestionOptions(UUID question_id) {
    return optionRepository.findAllQuestionOptions(question_id);
  }

  public Option findOptionById(UUID option_id) {
    return optionRepository.findOptionById(option_id);
  }

//  public void updateOption(Option option) {
//    optionRepository.updateOption(option);
//  }

  public void deleteOption(UUID option_id) {
    optionRepository.deleteOption(option_id);
  }

  public void inc(Option option) {
    option.inc();
    optionRepository.updateOption(option);
  }
}
