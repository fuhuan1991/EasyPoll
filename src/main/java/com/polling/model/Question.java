package com.polling.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.polling.QuestionType;
import com.polling.model.Option;

import java.util.List;
import java.util.UUID;

public class Question {
  private UUID id;
  private int index;
  private String poll_id;
  private String name;
  private String type;
  private List<Option> options;

  public Question(
          @JsonProperty("id") UUID id,
          @JsonProperty("index") int index,
          @JsonProperty("poll_id") String poll_id,
          @JsonProperty("name") String name,
          @JsonProperty("type") String type,
          @JsonProperty("options") List<Option> options) {
    this.id = id;
    this.index = index;
    this.poll_id = poll_id;
    this.name = name;
    this.type = type;
    this.options = options;
  }

  public UUID getId() {
    return id;
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public List<Option> getOptions() {
    return options;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setOptions(List<Option> options) {
    this.options = options;
  }

  public String getPoll_id() {
    return poll_id;
  }

  public void setPoll_id(String poll_id) {
    this.poll_id = poll_id;
  }

  @Override
  public String toString() {
    return "Question{" +
            "id=" + id +
            ", poll_id='" + poll_id + '\'' +
            ", name='" + name + '\'' +
            ", type=" + type +
            ", options=" + options +
            '}';
  }

  public void show() {
    System.out.println("  " + this.name + " " + this.type);
    for (Option o : this.options) {
      o.show();
    }
  }
}
