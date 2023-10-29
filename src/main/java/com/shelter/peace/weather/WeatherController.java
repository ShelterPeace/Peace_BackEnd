package com.shelter.peace.weather;

import com.shelter.peace.dto.ResponseDTO;
import com.shelter.peace.weather.dto.TodayWeatherResponseDTO;
import com.shelter.peace.weather.dto.WeekWeatherResponseDTO;
import com.shelter.peace.weather.service.OpenWeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WeatherController {
    private final OpenWeatherService openWeatherService;

    @GetMapping("/today/weather")
    public ResponseEntity<?> getNowWeatherInfo(@RequestParam(value = "lat") double lat,
                                               @RequestParam(value = "lon") double lon) {
        ResponseDTO<TodayWeatherResponseDTO> responseDTO = new ResponseDTO<>();

        responseDTO.setItem(openWeatherService.getNowWeather(lat, lon));
        responseDTO.setStatusCode(HttpStatus.OK.value());

        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/week/weather")
    public ResponseEntity<?> getWeekWeatherInfo(@RequestParam(value = "lat") double lat,
                                                @RequestParam(value = "lon") double lon) {
        ResponseDTO<WeekWeatherResponseDTO> responseDTO = new ResponseDTO<>();

        responseDTO.setItems(openWeatherService.getWeekWeather(lat, lon));
        responseDTO.setStatusCode(HttpStatus.OK.value());

        return ResponseEntity.ok().body(responseDTO);
    }


}
