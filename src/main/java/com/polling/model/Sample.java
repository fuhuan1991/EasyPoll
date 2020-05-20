package com.polling.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedList;

public class Sample {
  private LinkedList<String> optionIds;

  public Sample(@JsonProperty("optionIds") LinkedList<String> optionIds) {
    this.optionIds = optionIds;
  }

  public LinkedList<String> getOptionIds() {
    return optionIds;
  }

  public void setOptionIds(LinkedList<String> optionIds) {
    this.optionIds = optionIds;
  }

  @Override
  public String toString() {
    return "Sample{" +
            "optionIds=" + optionIds +
            '}';
  }
}
