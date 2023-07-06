package com.sapient.weather.prediction.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.sapient.weather.prediction.constant.Constant.ADVICE;
import static com.sapient.weather.prediction.constant.Constant.CLASS_NAME;
import static com.sapient.weather.prediction.constant.Constant.FIELD_NAME;
import static com.sapient.weather.prediction.constant.Constant.OPERATOR;
import static com.sapient.weather.prediction.constant.Constant.RULES;
import static com.sapient.weather.prediction.constant.Constant.VALUE;
import static com.sapient.weather.prediction.constant.Constant.YAML_FILE_NOT_FOUND;

@Getter
@Configuration
public class ConditionRuleConfig {
  private final List<Condition> conditions;

  private static final String KEY = "conditions";

  public ConditionRuleConfig(@Value("${conditions.file}") String conditionsFile) {
    InputStream inputStream = getClass().getClassLoader().getResourceAsStream(conditionsFile);
    if (Objects.isNull(inputStream)) {
      throw new RuntimeException(YAML_FILE_NOT_FOUND);
    }

    Yaml yaml = new Yaml();
    Map<String, List<Map<String, Object>>> conditionsMap = yaml.load(inputStream);
    this.conditions = mapToConditions(conditionsMap.get(KEY));
  }

  private List<Condition> mapToConditions(List<Map<String, Object>> conditionMaps) {
    return conditionMaps.stream()
        .map(conditionMap -> {
          String fieldName = (String) conditionMap.get(FIELD_NAME);
          String className = (String) conditionMap.get(CLASS_NAME);
          List<Map<String, String>> ruleMaps = (List<Map<String, String>>) conditionMap.get(RULES);
          List<Rule> rules = mapToRules(ruleMaps);
          return new Condition(fieldName, className, rules);
        })
        .collect(Collectors.toList());
  }

  private List<Rule> mapToRules(List<Map<String, String>> ruleMaps) {
    return ruleMaps.stream()
        .map(ruleMap -> {
          String operator = ruleMap.get(OPERATOR);
          String value = ruleMap.get(VALUE);
          String advice = ruleMap.get(ADVICE);
          return new Rule(operator, value, advice);
        })
        .collect(Collectors.toList());
  }
}
