package com.sapient.weather.prediction.factory;

import com.sapient.weather.prediction.service.evaluator.IWeatherConditionEvaluator;
import com.sapient.weather.prediction.service.evaluator.MaxTemperatureConditionEvaluator;
import com.sapient.weather.prediction.service.evaluator.WeatherTypeConditionEvaluator;
import com.sapient.weather.prediction.service.evaluator.WindSpeedConditionEvaluator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WeatherConditionEvaluatorFactoryTest {
  @Autowired
  private WeatherConditionEvaluatorFactory factory;

  @Test
  void testGetEvaluator_ShouldReturnCorrespondingClassObjectForGivenFiledName() {
    IWeatherConditionEvaluator maxTemperatureConditionEvaluator = factory.getEvaluator("MaxTemperatureConditionEvaluator");
    IWeatherConditionEvaluator weatherTypeConditionEvaluator = factory.getEvaluator("WeatherTypeConditionEvaluator");
    IWeatherConditionEvaluator windSpeedConditionEvaluator = factory.getEvaluator("WindSpeedConditionEvaluator");

    Assertions.assertEquals(maxTemperatureConditionEvaluator.getClass(), MaxTemperatureConditionEvaluator.class);
    Assertions.assertEquals(weatherTypeConditionEvaluator.getClass(), WeatherTypeConditionEvaluator.class);
    Assertions.assertEquals(windSpeedConditionEvaluator.getClass(), WindSpeedConditionEvaluator.class);
  }
}
