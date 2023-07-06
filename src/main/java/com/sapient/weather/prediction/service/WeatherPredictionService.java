package com.sapient.weather.prediction.service;

import com.sapient.weather.prediction.client.OpenWeatherAPIClient;
import com.sapient.weather.prediction.client.response.OpenWeatherAPIResponse;
import com.sapient.weather.prediction.configuration.Condition;
import com.sapient.weather.prediction.configuration.ConditionRuleConfig;
import com.sapient.weather.prediction.dto.WeatherPredictionDTO;
import com.sapient.weather.prediction.dto.WeatherPredictionDataDTO;
import com.sapient.weather.prediction.factory.WeatherConditionEvaluatorFactory;
import com.sapient.weather.prediction.service.evaluator.IWeatherConditionEvaluator;
import com.sapient.weather.prediction.transformer.WeatherAPIResponseToDTOTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.sapient.weather.prediction.constant.Constant.BAD_REQUEST_MESSAGE;
import static com.sapient.weather.prediction.constant.Constant.CITY_NOT_FOUND_MESSAGE;
import static com.sapient.weather.prediction.constant.Constant.SOME_THING_WENT_WRONG_MESSAGE;

@Service
public class WeatherPredictionService {
  private final OpenWeatherAPIClient weatherAPIClient;
  private final WeatherConditionEvaluatorFactory weatherConditionEvaluatorFactory;
  private final ConditionRuleConfig conditionRuleConfig;
  private final String appId;
  private final String count;

  private static final Logger LOGGER = LoggerFactory.getLogger(WeatherPredictionService.class);

  public WeatherPredictionService(OpenWeatherAPIClient weatherAPIClient,
                                  WeatherConditionEvaluatorFactory weatherConditionEvaluatorFactory,
                                  ConditionRuleConfig conditionRuleConfig,
                                  @Value("${open-weather-api-client.appId}") String appId,
                                  @Value("${open-weather-api-client.count}") String count) {
    this.weatherAPIClient = weatherAPIClient;
    this.weatherConditionEvaluatorFactory = weatherConditionEvaluatorFactory;
    this.conditionRuleConfig = conditionRuleConfig;
    this.appId = appId;
    this.count = count;
  }

  public WeatherPredictionDTO forecast(String city, String unit) {
    Call<OpenWeatherAPIResponse> weatherAPICall = this.weatherAPIClient.getWeatherForGivenCity(city, appId, count, unit);
    Response<OpenWeatherAPIResponse> weatherAPIResponse;

    try {
      weatherAPIResponse = weatherAPICall.execute();
    } catch (IOException ioException) {
      LOGGER.error("error occurred while calling weather API", ioException);
      return new WeatherPredictionDTO(Boolean.FALSE, HttpStatus.INTERNAL_SERVER_ERROR.value(), SOME_THING_WENT_WRONG_MESSAGE, Collections.emptyList());
    }

    if (Objects.nonNull(weatherAPIResponse) && weatherAPIResponse.isSuccessful()) {
      LOGGER.info("success response received from open weather API for city: {}", city);
      List<WeatherPredictionDataDTO> weatherPredictionDataDTOs = WeatherAPIResponseToDTOTransformer.transform(weatherAPIResponse.body());
      evaluateConditions(weatherPredictionDataDTOs);
      return new WeatherPredictionDTO(Boolean.TRUE, HttpStatus.OK.value(), null, weatherPredictionDataDTOs);
    }

    return this.handleFailureResponse(city, weatherAPIResponse);
  }

  private WeatherPredictionDTO handleFailureResponse(String city, Response<OpenWeatherAPIResponse> weatherApiResponse) {
    if (weatherApiResponse == null) {
      LOGGER.debug("empty response received from open weather API for city: {}", city);
      return new WeatherPredictionDTO(Boolean.FALSE, HttpStatus.BAD_GATEWAY.value(), SOME_THING_WENT_WRONG_MESSAGE, Collections.emptyList());
    }

    int weatherAPIResponseStatus = weatherApiResponse.code();
    LOGGER.debug("failure response received from open weather API for city: {} with HTTP status code: {}", city, weatherAPIResponseStatus);

    if (Objects.equals(weatherAPIResponseStatus, HttpStatus.BAD_REQUEST.value())) {
      return new WeatherPredictionDTO(Boolean.FALSE, HttpStatus.BAD_REQUEST.value(), BAD_REQUEST_MESSAGE, Collections.emptyList());
    }

    if (Objects.equals(weatherAPIResponseStatus, HttpStatus.NOT_FOUND.value())) {
      return new WeatherPredictionDTO(Boolean.FALSE, HttpStatus.NOT_FOUND.value(), CITY_NOT_FOUND_MESSAGE, Collections.emptyList());
    }

    if (Objects.equals(weatherAPIResponseStatus, HttpStatus.INTERNAL_SERVER_ERROR.value())) {
      return new WeatherPredictionDTO(Boolean.FALSE, HttpStatus.BAD_GATEWAY.value(), SOME_THING_WENT_WRONG_MESSAGE, Collections.emptyList());
    }

    return new WeatherPredictionDTO(Boolean.FALSE, HttpStatus.INTERNAL_SERVER_ERROR.value(), SOME_THING_WENT_WRONG_MESSAGE, Collections.emptyList());
  }

  private void evaluateConditions(List<WeatherPredictionDataDTO> weatherPredictionDataDTOs) {
    if (weatherPredictionDataDTOs == null) {
      return;
    }

    for (WeatherPredictionDataDTO weatherPredictionDataDTO : weatherPredictionDataDTOs) {
      List<Condition> conditions = this.conditionRuleConfig.getConditions();
      if (conditions == null) {
        return;
      }

      for (Condition condition : conditions) {
        IWeatherConditionEvaluator evaluator = this.weatherConditionEvaluatorFactory.getEvaluator(condition.getClassName());
        if (evaluator == null) {
          continue;
        }
        weatherPredictionDataDTO.setAdvices(evaluator.getAdvices(weatherPredictionDataDTO, condition));
      }
    }
  }
}
