package com.polling;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Question {
  private QuestionType type;
  private List<Option> options;

  public Question(
          @JsonProperty("type") QuestionType type,
          @JsonProperty("options") List<Option> options) {
    this.type = type;
    this.options = options;
  }

  public QuestionType getType() {
    return type;
  }

  public List<Option> getOptions() {
    return options;
  }

  public void setType(QuestionType type) {
    this.type = type;
  }

  public void setOptions(List<Option> options) {
    this.options = options;
  }

  @Override
  public String toString() {
    return "Question{" +
            "type=" + type +
            ", options=" + options +
            '}';
  }
}
