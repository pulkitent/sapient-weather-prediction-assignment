package com.sapient.weather.prediction.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class WeatherEntry {
  @JsonProperty("dt")
  private long unixTime;

  private Main main;

  @JsonProperty("weather")
  private List<Weather> weatherList;

  private Clouds clouds;

  private Wind wind;

  private int visibility;

  @JsonProperty("pop")
  private double probabilityOfPrecipitation;

  private Rain rain;

  private Sys sys;

  @JsonProperty("dt_txt")
  private String iSOTime;
}
