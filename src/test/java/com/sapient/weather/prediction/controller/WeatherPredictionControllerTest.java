package com.sapient.weather.prediction.controller;

import com.sapient.weather.prediction.dto.WeatherPredictionDTO;
import com.sapient.weather.prediction.service.WeatherPredictionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class WeatherPredictionControllerTest {

  @InjectMocks
  private WeatherPredictionController weatherPredictionController;

  @Mock
  private WeatherPredictionService weatherPredictionService;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testForecast_ShouldReturnWeatherPredictionDTOWithHTTPStatus200AndSuccessTrue() {
    // Arrange
    String city = "London";
    Optional<String> unit = Optional.of("metric");
    WeatherPredictionDTO expectedResponse = new WeatherPredictionDTO(true, HttpStatus.OK.value(), "", Collections.emptyList());
    when(weatherPredictionService.forecast(city, unit.get())).thenReturn(expectedResponse);

    // Act
    WeatherPredictionDTO weatherPredictionDTO = weatherPredictionController.forecast(city, unit);

    // Assert
    assertEquals(expectedResponse, weatherPredictionDTO);
    verify(weatherPredictionService, times(1)).forecast(city, unit.get());
  }

  @Test
  void testForecast_ShouldReturnWeatherPredictionDTOWithHTTPStatus400AndSuccessFalseWhenCityContainsNumbers() {
    // Arrange
    String city = "London123";
    Optional<String> unit = Optional.of("metric");

    // Act
    WeatherPredictionDTO weatherPredictionDTO = weatherPredictionController.forecast(city, unit);

    // Assert
    assertTrue(weatherPredictionDTO.getData().isEmpty());
    assertEquals(400, weatherPredictionDTO.getHttpStatusCode());
    assertEquals("Request parameters are incorrect", weatherPredictionDTO.getErrorMessage());
    assertFalse(weatherPredictionDTO.isSuccess());
    verify(weatherPredictionService, never()).forecast(anyString(), anyString());
  }
}
