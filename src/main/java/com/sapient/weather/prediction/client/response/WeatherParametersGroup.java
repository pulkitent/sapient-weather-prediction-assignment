package com.sapient.weather.prediction.client.response;

import com.fasterxml.jackson.annotation.JsonValue;

public enum WeatherParametersGroup {
  RAIN("Rain"),
  SNOW("Snow"),
  EXTREME("Extreme"),
  CLOUDS("Clouds"),
  CLEAR("Clear"),
  DRIZZLE("Drizzle"),
  THUNDERSTORM("Thunderstorm"),
  MIST("Mist"),
  SMOKE("Smoke"),
  HAZE("Haze"),
  DUST("Dust"),
  FOG("Fog"),
  SAND("Sand"),
  ASH("Ash"),
  SQUALL("Squall"),
  TORNADO("Tornado");

  private final String key;

  WeatherParametersGroup(String key) {
    this.key = key;
  }

  @JsonValue
  public String getKey() {
    return key;
  }
}
