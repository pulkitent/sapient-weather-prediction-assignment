package com.sapient.weather.prediction.transformer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sapient.weather.prediction.client.response.OpenWeatherAPIResponse;
import com.sapient.weather.prediction.dto.WeatherPredictionDataDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

class WeatherAPIResponseToDTOTransformerTest {
  private static final String TEST_RESPONSE_FILE_PATH = "src/test/java/resources/OpenWeatherAPIResponse.json";

  @Test
  void testTransform_shouldTransformWeatherAPIResponseToListOfDTOsSuccessfully() {
    // Arrange
    OpenWeatherAPIResponse weatherAPIResponse = getOpenWeatherAPIResponseResponse();

    // Act
    List<WeatherPredictionDataDTO> weatherPredictionDataDTOS = WeatherAPIResponseToDTOTransformer.transform(weatherAPIResponse);

    // Assert
    Assertions.assertNotNull(weatherPredictionDataDTOS);
    Assertions.assertEquals(1, weatherPredictionDataDTOS.size());
    WeatherPredictionDataDTO dto = weatherPredictionDataDTOS.get(0);
    Assertions.assertEquals(298.54, dto.getMaxTemperature());
    Assertions.assertEquals(296.87, dto.getMinTemperature());
    Assertions.assertEquals(0, dto.getAdvices().size());
    Assertions.assertEquals(LocalDate.of(2023, 6, 22), dto.getDate());
    Assertions.assertEquals(0.91, dto.getWindSpeed());
    Assertions.assertEquals(LocalTime.of(18, 0, 0), dto.getTimeWindow());
    Assertions.assertEquals("Clouds", dto.getWeatherTypePrediction());
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
