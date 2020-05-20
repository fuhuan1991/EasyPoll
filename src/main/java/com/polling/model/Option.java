package com.polling.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Option {
  private UUID id;
  private int index;
  private UUID question_id;
  private String name;
  private int count;

  public Option(
          @JsonProperty("id") UUID id,
          @JsonProperty("index") int index,
          @JsonProperty("question_id") UUID question_id,
          @JsonProperty("name") String name,
          @JsonProperty("count") int count) {
    this.id = id;
    this.index = index;
    this.question_id = question_id;
    this.name = name;
    this.count = count;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public UUID getQuestion_id() {
    return question_id;
  }

  public void setQuestion_id(UUID question_id) {
    this.question_id = question_id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public void inc() {
    this.count++;
  }

  public void show() {
    System.out.println("    " + this.name + " " + this.count);
  }

  @Override
  public String toString() {
    return "Option{" +
            "id=" + id +
            ", question_id=" + question_id +
            ", name='" + name + '\'' +
            ", count=" + count +
            '}';
  }
}
