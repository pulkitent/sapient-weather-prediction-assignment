package com.sapient.weather.prediction.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Rain {
  @JsonProperty("3h")
  private double rainVolume;
}
