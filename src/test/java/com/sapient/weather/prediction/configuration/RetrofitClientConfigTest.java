package com.sapient.weather.prediction.configuration;

import com.sapient.weather.prediction.client.OpenWeatherAPIClient;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class RetrofitClientConfigTest {
  @Autowired
  private RetrofitClientConfig retrofitClientConfig;

  @Test
  void testOpenWeatherAPIClientBeanCreation() {
    // Arrange & Act
    OpenWeatherAPIClient openWeatherAPIClient = retrofitClientConfig.openWeatherAPIClient();

    // Assert
    assertNotNull(openWeatherAPIClient);
  }

  @Test
  void testOkHttpClientBeanCreation() {
    // Arrange
    RetrofitClientConfig retrofitClientConfig = new RetrofitClientConfig("http://some-random-url.com");

    // Act
    OkHttpClient okHttpClient = retrofitClientConfig.okHttpClient();

    // Assert
    assertNotNull(okHttpClient);
  }
}
