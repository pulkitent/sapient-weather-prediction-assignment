package com.sapient.weather.prediction.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class City {
  private int id;

  private String name;

  private Coordinates coordinates;

  private String country;

  @JsonProperty("population")
  private int populationCount;

  private int timezone;

  @JsonProperty("sunrise")
  private long sunriseTime;

  @JsonProperty("sunset")
  private long sunsetTime;
}
