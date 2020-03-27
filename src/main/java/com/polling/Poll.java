package com.polling;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Poll {
  private String title;
  private List<Question> questions;

  public Poll(
          @JsonProperty("title") String title,
          @JsonProperty("questions") List<Question> questions) {
    this.title = title;
    this.questions = questions;
  }

  public String getTitle() {
    return title;
  }

  public List<Question> getQuestions() {
    return questions;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setQuestions(List<Question> questions) {
    this.questions = questions;
  }

  @Override
  public String toString() {
    return "Poll{" +
            "title='" + title + '\'' +
            ", questions=" + questions +
            '}';
  }
}
