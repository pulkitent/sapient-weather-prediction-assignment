package com.sapient.weather.prediction.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
public class WeatherPredictionDataDTO {
  private double maxTemperature;
  private double minTemperature;
  private List<String> advices;
  private LocalDate date;
  private double windSpeed;
  private LocalTime timeWindow;
  private String weatherTypePrediction;

  public void setAdvices(List<String> advices) {
    if (this.advices.isEmpty()) {
      this.advices = advices;
      return;
    }
    this.advices.addAll(advices);
  }
}
