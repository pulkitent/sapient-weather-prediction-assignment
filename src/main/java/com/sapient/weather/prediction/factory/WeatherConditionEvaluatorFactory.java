package com.sapient.weather.prediction.factory;

import com.sapient.weather.prediction.service.evaluator.IWeatherConditionEvaluator;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class WeatherConditionEvaluatorFactory {
  private final Map<String, IWeatherConditionEvaluator> evaluatorMap;

  public WeatherConditionEvaluatorFactory(List<IWeatherConditionEvaluator> evaluators) {
    this.evaluatorMap = evaluators.stream()
        .collect(Collectors.toMap(evaluator -> evaluator.getClass().getSimpleName(), Function.identity()));
  }

  public IWeatherConditionEvaluator getEvaluator(String fieldName) {
    return this.evaluatorMap.get(fieldName);
  }
}
