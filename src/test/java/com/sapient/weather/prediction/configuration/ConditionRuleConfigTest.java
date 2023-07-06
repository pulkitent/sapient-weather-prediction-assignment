package com.sapient.weather.prediction.configuration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ConditionRuleConfigTest {
  @Autowired
  private ConditionRuleConfig conditionRuleConfig;

  @Test
  void testGetConditions_shouldLoadAndDeserializeConditionsYamlFileSuccessfully() {
    // Arrange & Act
    List<Condition> conditions = conditionRuleConfig.getConditions();

    // Assert
    Assertions.assertNotNull(conditions);
    Assertions.assertEquals(3, conditions.size());

    Condition condition1 = conditions.get(0);
    Assertions.assertEquals("WeatherType", condition1.getFieldName());
    Assertions.assertEquals("WeatherTypeConditionEvaluator", condition1.getClassName());
    Assertions.assertEquals(3, condition1.getRules().size());

    Condition condition2 = conditions.get(1);
    Assertions.assertEquals("MaxTemperature", condition2.getFieldName());
    Assertions.assertEquals("MaxTemperatureConditionEvaluator", condition2.getClassName());
    Assertions.assertEquals(1, condition2.getRules().size());

    Condition condition3 = conditions.get(2);
    Assertions.assertEquals("WindSpeed", condition3.getFieldName());
    Assertions.assertEquals("WindSpeedConditionEvaluator", condition3.getClassName());
    Assertions.assertEquals(1, condition3.getRules().size());
  }

  @Test
  void testConditionRuleConfig_shouldThrowWhenUnableToFindYamlFile() {
    // Arrange & Act
    RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () -> new ConditionRuleConfig("some-random.yaml"));

    // Assert
    Assertions.assertEquals("unable to find/access file conditions.yaml in resources directory", runtimeException.getMessage());
  }
}
