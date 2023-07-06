package com.sapient.weather.prediction.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Weather {
  private int id;

  @JsonProperty("main")
  private WeatherParametersGroup weatherParametersGroup;

  private String description;

  private String icon;
}
