package com.sapient.weather.prediction.service.evaluator;

import com.sapient.weather.prediction.configuration.Condition;
import com.sapient.weather.prediction.configuration.Rule;
import com.sapient.weather.prediction.constant.Constant;
import com.sapient.weather.prediction.dto.WeatherPredictionDataDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class WindSpeedConditionEvaluator implements IWeatherConditionEvaluator {

  private static final Logger LOGGER = LoggerFactory.getLogger(WindSpeedConditionEvaluator.class);

  @Override
  public List<String> getAdvices(WeatherPredictionDataDTO weatherPredictionDTO, Condition condition) {
    List<String> advices = new LinkedList<>();

    for (Rule rule : condition.getRules()) {
      switch (rule.getOperator()) {
        case Constant.EQUAL:
          if (Double.compare(weatherPredictionDTO.getWindSpeed(), Double.parseDouble(rule.getValue())) == 0) {
            advices.add(rule.getAdvice());
          }
          break;
        case Constant.GREATER_THAN:
          if (Double.compare(weatherPredictionDTO.getWindSpeed(), Double.parseDouble(rule.getValue())) > 0) {
            advices.add(rule.getAdvice());
          }
          break;
        case Constant.LESS_THAN:
          if (Double.compare(weatherPredictionDTO.getWindSpeed(), Double.parseDouble(rule.getValue())) < 0) {
            advices.add(rule.getAdvice());
          }
          break;
        default:
          LOGGER.info("Operator not supported");
      }
    }
    return advices;
  }
}
