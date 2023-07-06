package com.sapient.weather.prediction.configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sapient.weather.prediction.client.OpenWeatherAPIClient;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
public class RetrofitClientConfig {
  private final String openWeatherAPIClientBaseUrl;

  public RetrofitClientConfig(@Value("${open-weather-api-client.url}") String openWeatherAPIClientBaseUrl) {
    this.openWeatherAPIClientBaseUrl = openWeatherAPIClientBaseUrl;
  }

  @Bean
  public OpenWeatherAPIClient openWeatherAPIClient() {
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(openWeatherAPIClientBaseUrl)
        .client(okHttpClient())
        .addConverterFactory(JacksonConverterFactory.create(jacksonDeserializer()))
        .build();

    return retrofit.create(OpenWeatherAPIClient.class);
  }

  @Bean
  public OkHttpClient okHttpClient() {
    return new OkHttpClient();
  }

  private ObjectMapper jacksonDeserializer() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    return objectMapper;
  }
}
