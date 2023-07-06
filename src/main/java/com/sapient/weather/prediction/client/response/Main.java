package com.sapient.weather.prediction.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Main {
  @JsonProperty("temp")
  private double temperature;

  @JsonProperty("feels_like")
  private double humanPerceptionOfTemperature;

  @JsonProperty("temp_min")
  private double minTemperature;

  @JsonProperty("temp_max")
  private double maxTemperature;

  @JsonProperty("pressure")
  private int defaultSeaLevelAtmosphericPressure;

  @JsonProperty("sea_level")
  private int seaLevelAtmosphericPressure;

  @JsonProperty("grnd_level")
  private int groundLevelAtmosphericPressure;

  private int humidity;

  @JsonProperty("temp_kf")
  private double internalParameter;
}
