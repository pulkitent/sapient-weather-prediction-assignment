package com.sapient.weather.prediction.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Clouds {
  @JsonProperty("all")
  private int cloudinessPercentage;
}
