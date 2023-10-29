package com.shelter.peace.weather.service;

import com.shelter.peace.weather.dto.WeatherDataDTO;
import com.shelter.peace.weather.dtoNow.OpenWeatherResponseDTO;
import com.shelter.peace.weather.dtoWeek.WeekWeatherItemDTO;
import com.shelter.peace.weather.dtoWeek.WeekWeatherResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OpenWeatherService {
    public final String weatherUrl = "https://api.openweathermap.org/data/2.5/";
    public final String now = "weather";
    public final String week = "forecast";
    public final double aaa = 273.15;

    @Value("${open.weather.map.api.key}")
    public String apiKey;

    public void getNowWeather(double lat, double lon) {
        try {
            StringBuilder stringBuilder = new StringBuilder()
                    .append(weatherUrl)
                    .append(now)
                    .append("?lat=")
                    .append(lat)
                    .append("&lon=")
                    .append(lon)
                    .append("&lang=kr")
                    .append("&units=metric")
                    .append("&appid=")
                    .append(apiKey);

            RestTemplate restTemplate = new RestTemplate();
            OpenWeatherResponseDTO json = restTemplate.getForObject(new URI(stringBuilder.toString()), OpenWeatherResponseDTO.class);

            System.out.println("기온: " + json.getMain().getTemp());
            System.out.println("설명: " + json.getWeather().get(0).getDescription());

            System.out.println("체감 온도: " + json.getMain().getFeels_like());
            System.out.println("최저 온도: " + json.getMain().getTemp_min());
            System.out.println("최고 온도: " + json.getMain().getTemp_max());
            System.out.println("나라:" + json.getSys().getCountry());
            System.out.println("습도:" + json.getMain().getHumidity() + "%");
            System.out.println("바람: " + json.getWind().getSpeed() + "m/s");
            System.out.println("구름: " + json.getClouds().getAll() + "%");
            if (json.getRain() != null) {
                System.out.println("비: " + json.getRain().getRain1h());
            } else {
                System.out.println("비 안 옴");
            }


            System.out.println(json);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public void getWeekWeather(double lat, double lon) {
        try {
            StringBuilder stringBuilder = new StringBuilder()
                    .append(weatherUrl)
                    .append(week)
                    .append("?lat=")
                    .append(lat)
                    .append("&lon=")
                    .append(lon)
                    .append("&lang=kr")
                    .append("&units=metric")
                    .append("&appid=")
                    .append(apiKey);

            RestTemplate restTemplate = new RestTemplate();

            WeekWeatherResponseDTO json = restTemplate.getForObject(new URI(stringBuilder.toString()), WeekWeatherResponseDTO.class);

            json.getList().stream().forEach(
                    dto -> System.out.println("dt: " + dto.getDt() + ", 시간: " + dto.getDt_txt() + ", 온도: " + dto.getMain().getTemp() + ", 비: " + dto.getWeather().get(0).getMain())
            );

            System.out.println("------------날짜별 최저, 최고 온도 구하기");

            Map<LocalDate, List<WeekWeatherItemDTO>> groupedByDate = json.getList().stream()
                    .collect(Collectors.groupingBy(item -> Instant.ofEpochSecond(item.getDt()).atZone(ZoneId.systemDefault()).toLocalDate()));

            groupedByDate.forEach((date, weatherList) -> {
                double minTemp = weatherList.stream()
                        .mapToDouble(item -> item.getMain().getTemp())
                        .min()
                        .orElse(Double.NaN);

                double maxTemp = weatherList.stream()
                        .mapToDouble(item -> item.getMain().getTemp())
                        .max()
                        .orElse(Double.NaN);

                System.out.println("날짜: " + date + ", 최저 온도: " + minTemp + ", 최고 온도: " + maxTemp);
            });

//            Map<String, List<WeekWeatherItemDTO>> groupedByDate = json.getList().stream()
//                    .collect(Collectors.groupingBy(item -> item.getDt_txt().substring(0, 10)));
//
//            groupedByDate.forEach((date, weatherList) -> {
//                double minTemp = weatherList.stream()
//                        .mapToDouble(item -> item.getMain().getTemp())
//                        .min()
//                        .orElse(Double.NaN);
//
//                double maxTemp = weatherList.stream()
//                        .mapToDouble(item -> item.getMain().getTemp())
//                        .max()
//                        .orElse(Double.NaN);
//
//                System.out.println("날짜: " + date + ", 최저 온도: " + minTemp + ", 최고 온도: " + maxTemp);
//            });

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
