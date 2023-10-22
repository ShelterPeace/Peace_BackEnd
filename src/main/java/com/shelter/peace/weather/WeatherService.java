package com.shelter.peace.weather;

import com.shelter.peace.weather.dto.WeatherDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class WeatherService {
    @Value("${weather.service.key}")
    private String serviceKey;

    private final String ENCODE = "UTF-8";
    private String weatherUrl = "https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst";

    public void getWeather(int nx, int ny) {
        try {
            String dateTime = getBaseDateTime();

            String baseDate = dateTime.substring(0, 8);
            String baseTime = dateTime.substring(8);

            StringBuilder stringBuilder = new StringBuilder()
                    .append(weatherUrl)
                    .append("?ServiceKey=" + URLEncoder.encode(serviceKey, ENCODE))
                    .append("&pageNo=" + URLEncoder.encode("1", ENCODE))
                    .append("&numOfRows=" + URLEncoder.encode("1000", ENCODE))
                    .append("&dataType=JSON")
                    .append("&base_date=" + URLEncoder.encode(baseDate, ENCODE))
                    .append("&base_time=" + URLEncoder.encode(baseTime, ENCODE))
                    .append("&nx=" + URLEncoder.encode(String.valueOf(nx), ENCODE))
                    .append("&ny=" + URLEncoder.encode(String.valueOf(ny), ENCODE));

            RestTemplate restTemplate = new RestTemplate();
            WeatherDTO weatherResponse = restTemplate.getForObject(new URI(stringBuilder.toString()), WeatherDTO.class);

            weatherResponse.getResponse().getBody().getItems().getItem().stream()
                    .forEach(System.out::println);

        } catch (UnsupportedEncodingException e) {
            System.out.println("UnsupportedEncodingException: ");
            System.out.print(e.getMessage());
            throw new RuntimeException(e);
        } catch (Exception e) {
            System.out.println("Exception: ");
            System.out.print(e.getMessage());
            throw new RuntimeException(e);
        }
    }


    public String getBaseDateTime() {
        // 현재 날짜와 시간을 LocalDateTime 객체로 가져오기
        LocalDateTime now = LocalDateTime.now();

        // 현재 시간과 분 추출
        int currentHour = now.getHour();
        int currentMinute = now.getMinute();

        // API 제공 시간 리스트
        int[] apiHours = {2, 5, 8, 11, 14, 17, 20, 23};

        // 현재 시간 이전의 API 제공 시간 찾기
        int closestApiHour = apiHours[0];
        for (int apiHour : apiHours) {
            if (currentHour < apiHour || (currentHour == apiHour && currentMinute < 10)) {
                break;
            }
            closestApiHour = apiHour;
        }

        // 전날 날짜로 변경
        LocalDateTime apiTime;
        if (currentHour < closestApiHour || (currentHour == closestApiHour && currentMinute < 10)) {
            apiTime = now.withHour(closestApiHour).withMinute(0).withSecond(0).withNano(0).minusDays(1);
        } else {
            apiTime = now.withHour(closestApiHour).withMinute(0).withSecond(0).withNano(0);
        }

        // API 제공 시간을 붙여서 반환 (0200, 0500, 0800, 1100, 1400, 1700, 2000, 2300 중 하나)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        return apiTime.format(formatter);
    }



}
