package com.sapient.weather.prediction.transformer;

import com.sapient.weather.prediction.client.response.Main;
import com.sapient.weather.prediction.client.response.OpenWeatherAPIResponse;
import com.sapient.weather.prediction.client.response.WeatherEntry;
import com.sapient.weather.prediction.client.response.WeatherParametersGroup;
import com.sapient.weather.prediction.dto.WeatherPredictionDataDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WeatherAPIResponseToDTOTransformer {

  private static final String DATE_TIME_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

  private WeatherAPIResponseToDTOTransformer() {
    throw new IllegalStateException("Should not instantiate a utility class");
  }

  public static List<WeatherPredictionDataDTO> transform(OpenWeatherAPIResponse weatherAPIResponse) {
    List<WeatherEntry> weatherEntries = weatherAPIResponse.getWeatherEntries();
    List<WeatherPredictionDataDTO> weatherPredictionsData = new ArrayList<>();

    for (WeatherEntry weatherEntry : weatherEntries) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT_PATTERN);
      LocalDate date = LocalDateTime.parse(weatherEntry.getISOTime(), formatter).toLocalDate();
      LocalTime time = LocalDateTime.parse(weatherEntry.getISOTime(), formatter).toLocalTime();
      Main main = weatherEntry.getMain();
      double maxTemperature = main.getMaxTemperature();
      double minTemperature = main.getMinTemperature();

      WeatherParametersGroup weatherParametersGroup = weatherEntry.getWeatherList().get(0).getWeatherParametersGroup();
      double speed = weatherEntry.getWind().getSpeed();

      WeatherPredictionDataDTO currentPrediction = WeatherPredictionDataDTO.builder()
          .maxTemperature(maxTemperature)
          .minTemperature(minTemperature)
          .advices(Collections.emptyList())
          .date(date)
          .windSpeed(speed)
          .timeWindow(time)
          .weatherTypePrediction(weatherParametersGroup.getKey())
          .build();
      weatherPredictionsData.add(currentPrediction);
    }

    return weatherPredictionsData;
  }
}
