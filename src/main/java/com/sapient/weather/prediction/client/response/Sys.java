package com.sapient.weather.prediction.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Sys {
  @JsonProperty("pod")
  private PartOfDay partOfDay;
}
