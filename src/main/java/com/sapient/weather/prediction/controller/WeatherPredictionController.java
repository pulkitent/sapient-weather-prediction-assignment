package com.sapient.weather.prediction.controller;

import com.sapient.weather.prediction.dto.WeatherPredictionDTO;
import com.sapient.weather.prediction.service.WeatherPredictionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Optional;

import static com.sapient.weather.prediction.constant.Constant.BAD_REQUEST_MESSAGE;
import static com.sapient.weather.prediction.constant.Constant.METRIC;

@RestController
@RequestMapping("/api")
@Api(tags = "Weather Prediction API", produces = "Predicts next 3 days weather forecast", protocols = "http")
public class WeatherPredictionController {
  private final WeatherPredictionService weatherPredictionService;

  private static final Logger LOGGER = LoggerFactory.getLogger(WeatherPredictionController.class);

  @Autowired
  public WeatherPredictionController(WeatherPredictionService weatherPredictionService) {
    this.weatherPredictionService = weatherPredictionService;
  }

  @GetMapping("/v1/weathers")
  @ApiOperation(value = "Get weather forecast")
  @ApiResponses({@ApiResponse(code = 200, message = "", response = WeatherPredictionDTO.class),
      @ApiResponse(code = 400, message = "Request parameters are incorrect"),
      @ApiResponse(code = 404, message = "Given city is unavailable"),
      @ApiResponse(code = 500, message = "Something went wrong, please contact support team"),
      @ApiResponse(code = 502, message = "Something went wrong, please contact support team")})
  public WeatherPredictionDTO forecast(@RequestParam(value = "city") String city,
                                       @RequestParam(value = "unit", required = false) Optional<String> unit) {
    LOGGER.info("request received for weather forecast of city: {} with unit: {}", city, unit);

    if (city.matches(".*\\d+.*")) {
      return new WeatherPredictionDTO(Boolean.FALSE, HttpStatus.BAD_REQUEST.value(), BAD_REQUEST_MESSAGE, Collections.emptyList());
    }

    return this.weatherPredictionService.forecast(city, unit.orElse(METRIC));
  }
}
