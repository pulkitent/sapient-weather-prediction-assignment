package com.sapient.weather.prediction.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Condition {
  private final String fieldName;
  private final String className;
  private final List<Rule> rules;
}
