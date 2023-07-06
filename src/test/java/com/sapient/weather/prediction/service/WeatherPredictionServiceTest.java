package com.sapient.weather.prediction.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sapient.weather.prediction.client.OpenWeatherAPIClient;
import com.sapient.weather.prediction.client.response.OpenWeatherAPIResponse;
import com.sapient.weather.prediction.configuration.ConditionRuleConfig;
import com.sapient.weather.prediction.dto.WeatherPredictionDTO;
import com.sapient.weather.prediction.factory.WeatherConditionEvaluatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import retrofit2.Call;
import retrofit2.Response;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
class WeatherPredictionServiceTest {

  @Autowired
  private WeatherPredictionService weatherPredictionService;

  @Mock
  private OpenWeatherAPIClient mockWeatherAPIClient;

  @Mock
  private WeatherConditionEvaluatorFactory mockWeatherConditionEvaluatorFactory;

  @Mock
  private ConditionRuleConfig mockConditionRuleConfig;

  @Mock
  private Call<OpenWeatherAPIResponse> mockWeatherAPICall;

  private final String appId = "your-app-id";
  private final String count = "your-count";
  private final String city = "London";
  private final String unit = "metric";

  private static final String TEST_RESPONSE_FILE_PATH = "src/test/java/resources/OpenWeatherAPIResponse.json";

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);

    weatherPredictionService = new WeatherPredictionService(mockWeatherAPIClient,
        mockWeatherConditionEvaluatorFactory, mockConditionRuleConfig, appId, count);
  }

  @Test
  void testForecast_ReturnsSuccessfulWeatherPredictionDTO() throws IOException {
    // Arrange
    // TODO mock static method
    OpenWeatherAPIResponse weatherAPIResponse = getOpenWeatherAPIResponseResponse();
    when(mockWeatherAPIClient.getWeatherForGivenCity(city, appId, count, unit)).thenReturn(mockWeatherAPICall);
    Response<OpenWeatherAPIResponse> weatherAPIResponseMock = Response.success(weatherAPIResponse);
    when(mockWeatherAPICall.execute()).thenReturn(weatherAPIResponseMock);
    when(mockConditionRuleConfig.getConditions()).thenReturn(Collections.emptyList());

    // Act
    WeatherPredictionDTO weatherPredictionDTO = weatherPredictionService.forecast(city, unit);

    // Assert
    assertFalse(weatherPredictionDTO.getData().isEmpty());
    assertEquals(200, weatherPredictionDTO.getHttpStatusCode());
    assertNull(weatherPredictionDTO.getErrorMessage());
    assertTrue(weatherPredictionDTO.isSuccess());
  }

  @Test
  void testForecast_ReturnsFailureWeatherPredictionDTO_WhenIOExceptionOccurs() throws IOException {
    // Arrange
    when(mockWeatherAPIClient.getWeatherForGivenCity(city, appId, count, unit)).thenReturn(mockWeatherAPICall);
    when(mockWeatherAPICall.execute()).thenThrow(new IOException());

    // Act
    WeatherPredictionDTO weatherPredictionDTO = weatherPredictionService.forecast(city, unit);

    // Assert
    assertFalse(weatherPredictionDTO.isSuccess());
    assertEquals(500, weatherPredictionDTO.getHttpStatusCode());
    assertEquals("Something went wrong, please contact support team", weatherPredictionDTO.getErrorMessage());
    assertTrue(weatherPredictionDTO.getData().isEmpty());
  }

  private OpenWeatherAPIResponse getOpenWeatherAPIResponseResponse() {
    ObjectMapper jacksonObjectMapper = new ObjectMapper();
    jacksonObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    jacksonObjectMapper.registerModule(new JavaTimeModule());

    try {
      return jacksonObjectMapper.readValue(new File(TEST_RESPONSE_FILE_PATH), OpenWeatherAPIResponse.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
