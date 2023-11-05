package com.shelter.peace.weather;

import com.shelter.peace.dto.ResponseDTO;
import com.shelter.peace.user.entity.UserDetailsImpl;
import com.shelter.peace.weather.dto.InterestAreaDTO;
import com.shelter.peace.weather.dto.TodayResponseDTO;
import com.shelter.peace.weather.dto.WeekResponseDTO;
import com.shelter.peace.weather.dtoWeek.WeekWeatherResponseDTO;
import com.shelter.peace.weather.service.AreaService;
import com.shelter.peace.weather.service.OpenWeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/weather")
public class WeatherController {
    private final OpenWeatherService openWeatherService;
    private final AreaService areaService;

    // 위도, 경도로 오늘 날씨 구하기
    @GetMapping("/today")
    public ResponseEntity<?> getNowWeatherInfo(@RequestParam(value = "lat") double lat,
                                               @RequestParam(value = "lon") double lon) {
        ResponseDTO<TodayResponseDTO> responseDTO = new ResponseDTO<>();

        responseDTO.setItem(openWeatherService.getNowWeather(lat, lon));
        responseDTO.setStatusCode(HttpStatus.OK.value());

        return ResponseEntity.ok().body(responseDTO);
    }

    // 위도, 경도로 주간 날씨 구하기
    @GetMapping("/week")
    public ResponseEntity<?> getWeekWeatherInfo(@RequestParam(value = "lat") double lat,
                                                @RequestParam(value = "lon") double lon) {
        ResponseDTO<WeekResponseDTO> responseDTO = new ResponseDTO<>();

        responseDTO.setItems(openWeatherService.getWeekWeather(lat, lon));
        responseDTO.setStatusCode(HttpStatus.OK.value());

        return ResponseEntity.ok().body(responseDTO);
    }

    // 신규 관심지역 넣기
    @PostMapping("/interest")
    public ResponseEntity<?> insertInterestArea(@AuthenticationPrincipal UserDetailsImpl userDetails,
                          @RequestBody InterestAreaDTO interestAreaDTO){
        interestAreaDTO.setUserNo(userDetails.getId());
        areaService.saveInterestArea(interestAreaDTO);

        return ResponseEntity.ok().build();
    }

    // 관심지역 수정
    @PutMapping("/interest")
    public ResponseEntity<?> updateInterestArea(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                       @RequestBody InterestAreaDTO interestAreaDTO){
        interestAreaDTO.setUserNo(userDetails.getId());
        areaService.updateInterestArea(interestAreaDTO);

        return ResponseEntity.ok().build();
    }

    // 관심지역 확인
    @GetMapping("/interest")
    public ResponseEntity<?> getInterestArea(@AuthenticationPrincipal UserDetailsImpl userDetails){
        ResponseDTO<InterestAreaDTO> responseDTO = new ResponseDTO<>();

        InterestAreaDTO interestAreaDTO = areaService.getInterestArea(userDetails.getId()).EntityTODTO();

        responseDTO.setItem(interestAreaDTO);
        responseDTO.setStatusCode(HttpStatus.OK.value());

        return ResponseEntity.ok().body(responseDTO);
    }

    // 사용자 관심 지역의 오늘 날씨 구하기
    @GetMapping("/interest/week")
    public ResponseEntity<?> getInterestToday(@AuthenticationPrincipal UserDetailsImpl userDetails){
        ResponseDTO<TodayResponseDTO> responseDTO = new ResponseDTO<>();
        List<TodayResponseDTO> todayResponseDTOList = new ArrayList<>();

        InterestAreaDTO interestAreaDTO = areaService.getInterestArea(userDetails.getId()).EntityTODTO();

        TodayResponseDTO area1 = openWeatherService.getNowWeather(interestAreaDTO.getArea1Lat(), interestAreaDTO.getArea1Lon());
        TodayResponseDTO area2 = openWeatherService.getNowWeather(interestAreaDTO.getArea2Lat(), interestAreaDTO.getArea2Lon());
        todayResponseDTOList.add(area1);
        todayResponseDTOList.add(area2);

        responseDTO.setItems(todayResponseDTOList);
        responseDTO.setStatusCode(HttpStatus.OK.value());

        return ResponseEntity.ok().body(responseDTO);
    }


    // 관리자가 지역 엑셀 파일 넣기
    @PostMapping("/area")
    public void uploadFile(@RequestParam("file") MultipartFile file) {

        areaService.parseExcel(file);
    }

}
