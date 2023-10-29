package com.shelter.peace.weather;

import com.shelter.peace.weather.service.OpenWeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WeatherController {
    private final OpenWeatherService openWeatherService;

    @GetMapping("/now/weather")
    public ResponseEntity<?> getNowWeatherInfo(@RequestParam(value = "lat") double lat,
                                               @RequestParam(value = "lon") double lon) {
        openWeatherService.getNowWeather(lat, lon);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/week/weather")
    public ResponseEntity<?> getWeekWeatherInfo(@RequestParam(value = "lat") double lat,
                                                @RequestParam(value = "lon") double lon) {
        openWeatherService.getWeekWeather(lat, lon);
        return ResponseEntity.ok().build();
    }


}
