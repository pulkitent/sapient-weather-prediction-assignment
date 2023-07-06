package com.sapient.weather.prediction.constant;

public class Constant {
  private Constant() {
    throw new IllegalStateException("Should not instantiate a constant class");
  }

  public static final String EQUAL = "Equal";
  public static final String NOT_EQUAL = "NotEqual";
  public static final String GREATER_THAN = "GreaterThan";
  public static final String LESS_THAN = "LessThan";

  public static final String BAD_REQUEST_MESSAGE = "Request parameters are incorrect";
  public static final String CITY_NOT_FOUND_MESSAGE = "Given city is unavailable";
  public static final String SOME_THING_WENT_WRONG_MESSAGE = "Something went wrong, please contact support team";

  public static final String YAML_FILE_NOT_FOUND = "unable to find/access file conditions.yaml in resources directory";

  public static final String FIELD_NAME = "fieldName";
  public static final String CLASS_NAME = "className";
  public static final String RULES = "rules";
  public static final String OPERATOR = "operator";
  public static final String VALUE = "value";
  public static final String ADVICE = "advice";

  public static final String METRIC = "metric";
}
