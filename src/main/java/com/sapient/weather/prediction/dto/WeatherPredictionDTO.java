package com.sapient.weather.prediction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class WeatherPredictionDTO {
  private boolean isSuccess;
  private int httpStatusCode;
  private String errorMessage;
  private List<WeatherPredictionDataDTO> data;
}
