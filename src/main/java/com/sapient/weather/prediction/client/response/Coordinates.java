package com.sapient.weather.prediction.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Coordinates {
  @JsonProperty("lat")
  private double latitude;

  @JsonProperty("lon")
  private double longitude;
}
