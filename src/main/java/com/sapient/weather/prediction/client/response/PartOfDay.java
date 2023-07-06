package com.sapient.weather.prediction.client.response;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PartOfDay {
  NIGHT("n"), DAY("d");

  private final String key;

  PartOfDay(String key) {
    this.key = key;
  }

  @JsonValue
  public String getKey() {
    return key;
  }
}
