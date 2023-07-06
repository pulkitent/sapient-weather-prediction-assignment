package com.sapient.weather.prediction.service.evaluator;

import com.sapient.weather.prediction.configuration.Condition;
import com.sapient.weather.prediction.configuration.Rule;
import com.sapient.weather.prediction.constant.Constant;
import com.sapient.weather.prediction.dto.WeatherPredictionDataDTO;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class WeatherTypeConditionEvaluator implements IWeatherConditionEvaluator {

  @Override
  public List<String> getAdvices(WeatherPredictionDataDTO weatherPredictionDTO, Condition condition) {
    List<String> advices = new LinkedList<>();

    for (Rule rule : condition.getRules()) {
      if (Constant.EQUAL.equals(rule.getOperator())
          && (weatherPredictionDTO.getWeatherTypePrediction().equals(rule.getValue()))) {
        advices.add(rule.getAdvice());
      }

      if (Constant.NOT_EQUAL.equals(rule.getOperator())
          && (!weatherPredictionDTO.getWeatherTypePrediction().equals(rule.getValue()))) {
        advices.add(rule.getAdvice());
      }
    }
    return advices;
  }
}
