package com.shelter.peace.weather;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WeatherController {
    private final WeatherService weatherService;

    @GetMapping("/weather")
    public ResponseEntity<?> getWeatherInfo() {
        weatherService.getWeather(60, 120);
        return ResponseEntity.ok().build();
    }

}
