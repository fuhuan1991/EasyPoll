package com.polling;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Option {
  private String name;

  public Option(@JsonProperty("name") String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Option{" +
            "name='" + name + '\'' +
            '}';
  }
}
