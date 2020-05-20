package com.polling.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Poll {
  private String id;
  private String title;
  private List<Question> questions;
  private String startTime;
  private String endTime;

  public Poll(
          @JsonProperty("id") String id,
          @JsonProperty("title") String title,
          @JsonProperty("questions") List<Question> questions,
          @JsonProperty("startTime") String startTime,
          @JsonProperty("endTime") String endTime) {
    this.id = id;
    this.title = title;
    this.questions = questions;
    this.startTime = startTime;
    this.endTime = endTime;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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

  public String getStartTime() {
    return this.startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public void show() {
    System.out.println(this.title);
    System.out.println(this.startTime + " ====> " + this.endTime);
    for (Question o : this.questions) {
      o.show();
    }
  }
}
