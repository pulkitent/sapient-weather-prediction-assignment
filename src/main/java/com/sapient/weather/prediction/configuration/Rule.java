package com.sapient.weather.prediction.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Rule {
  private final String operator;
  private final String value;
  private final String advice;
}
