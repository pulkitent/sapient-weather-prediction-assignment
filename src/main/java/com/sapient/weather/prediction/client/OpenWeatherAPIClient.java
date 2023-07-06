package com.sapient.weather.prediction.client;

import com.sapient.weather.prediction.client.response.OpenWeatherAPIResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface OpenWeatherAPIClient {

  @GET("/data/2.5/forecast")
  Call<OpenWeatherAPIResponse> getWeatherForGivenCity(@Query("q") String city,
                                                      @Query("appid") String appId,
                                                      @Query("cnt") String count,
                                                      @Query("units") String unit);
}
