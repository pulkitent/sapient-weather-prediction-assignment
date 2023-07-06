package com.sapient.weather.prediction.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class OpenWeatherAPIResponse {
  @JsonProperty("cod")
  private String httpStatusCode;

  private int message;

  @JsonProperty("cnt")
  private int numberOfDays;

  @JsonProperty("list")
  private List<WeatherEntry> weatherEntries;

  private City city;
}
