package com.sapient.weather.prediction.service.evaluator;

import com.sapient.weather.prediction.configuration.Condition;
import com.sapient.weather.prediction.dto.WeatherPredictionDataDTO;

import java.util.List;

public interface IWeatherConditionEvaluator {
  List<String> getAdvices(WeatherPredictionDataDTO weatherPredictionDTO, Condition condition);
}
