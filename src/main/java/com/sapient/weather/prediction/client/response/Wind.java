package com.sapient.weather.prediction.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Wind {
  private double speed;

  @JsonProperty("deg")
  private int windDirectionInDegrees;

  private double gust;
}
