package com.shelter.peace.shelterApi.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shelter.peace.shelterApi.repository.EarthquakeShelterRepository;
import com.shelter.peace.shelterApi.service.EarthquakeShelterService;
import com.shelter.peace.shelterApi.service.dto.EarthquakeOutdoorsShelterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EarthquakeShelterServiceImpl implements EarthquakeShelterService {

    @Value("${earthquake.shelter.url}")
    private String apiUrl;

    @Value("${api.serviceKey}")
    private String apiKey;

    @Autowired
    private EarthquakeShelterRepository earthquakeShelterRepository;

    @Override
    public void extractEarthquakeShelterData() {

        // API 호출을 위한 URL 생성
        String url = apiUrl + "?serviceKey=" + apiKey + "&pageNo=1&numOfRows=5&type=json";

        RestTemplate restTemplate = new RestTemplate();

        try {
            // API 호출 및 응답 받기
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            System.out.println("Status Code: " + responseEntity.getStatusCode());
            String responseBody = responseEntity.getBody();
            System.out.println("Response Body: " + responseBody);

            ObjectMapper objectMapper = new ObjectMapper();
            EarthquakeOutdoorsShelterResponse earthquakeOutdoorsShelterResponse = objectMapper.readValue(responseBody, EarthquakeOutdoorsShelterResponse.class);

            earthquakeOutdoorsShelterResponse.getEarthquakeOutdoorsShelters()
                    .forEach(System.out::println);

//            ObjectMapper objectMapper = new ObjectMapper();
////            try {
//                EarthquakeShelterResponseDTO earthquakeShelterResponseDTO = objectMapper.readValue(responseBody, EarthquakeShelterResponseDTO.class);
//                System.out.println("Parsed Response: " + earthquakeShelterResponseDTO);
//            } catch (JsonProcessingException e) {
//                e.printStackTrace();
//            }
//            System.out.println(response.getBody().getEarthquakeOutdoorsShelter());

//            // JSON 응답 데이터 출력
//            List<EarthquakeShelterDTO> jsonResponse = response.getBody().getEarthquakeOutdoorsShelter().get(1).getRow();
//            System.out.println("Received JSON data: \n" + jsonResponse);
//
//            for(EarthquakeShelterDTO a : jsonResponse){
//                System.out.println(a.getArcd());
//            }

//            return jsonResponse;

//            // JSON 데이터 파싱
//            ObjectMapper objectMapper = new ObjectMapper();
//            EarthquakeShelterResponseDTO responseDTO = objectMapper.readValue(jsonResponse, EarthquakeShelterResponseDTO.class);
//
//            System.out.println("Response DTO: " + responseDTO.toString());
//
//            // 파싱된 데이터 사용
//            if (responseDTO != null && responseDTO.getEarthquakeOutdoorsShelter() != null) {
//                List<EarthquakeShelterDTO> row = responseDTO.getEarthquakeOutdoorsShelter().get(0).getRow();
//
//                for (EarthquakeShelterDTO data : row) {
//                    // 필요한 정보 추출하여 리스트에 추가
//                    EarthquakeShelterDTO extractedData = new EarthquakeShelterDTO();
//                    extractedData.setArcd(data.getArcd());
//                    extractedData.setAcmdfclty_sn(data.getAcmdfclty_sn());
//                    extractedData.setCtprvn_nm(data.getCtprvn_nm());
//                    extractedData.setSgg_nm(data.getSgg_nm());
//                    extractedData.setVt_acmdfclty_nm(data.getVt_acmdfclty_nm());
//                    extractedData.setRdnmadr_cd(data.getRdnmadr_cd());
//                    extractedData.setBdong_cd(data.getBdong_cd());
//                    extractedData.setHdong_cd(data.getHdong_cd());
//                    extractedData.setDtl_adres(data.getDtl_adres());
//                    extractedData.setFclty_ar(data.getFclty_ar());
//                    extractedData.setXcord(data.getXcord());
//                    extractedData.setYcord(data.getYcord());
//
//                    extractedDataList.add(extractedData);
//
//                    // 필요한 정보 추출하여 엔티티에 저장
//                    EarthquakeShelterData earthquakeShelterEntity = new EarthquakeShelterData();
//                    earthquakeShelterEntity.setArcd(data.getArcd());
//                    earthquakeShelterEntity.setAcmdfclty_sn(data.getAcmdfclty_sn());
//                    earthquakeShelterEntity.setCtprvn_nm(data.getCtprvn_nm());
//                    earthquakeShelterEntity.setSgg_nm(data.getSgg_nm());
//                    earthquakeShelterEntity.setVt_acmdfclty_nm(data.getVt_acmdfclty_nm());
//                    earthquakeShelterEntity.setRdnmadr_cd(data.getRdnmadr_cd());
//                    earthquakeShelterEntity.setBdong_cd(data.getBdong_cd());
//                    earthquakeShelterEntity.setHdong_cd(data.getHdong_cd());
//                    earthquakeShelterEntity.setDtl_adres(data.getDtl_adres());
//                    earthquakeShelterEntity.setFclty_ar(data.getFclty_ar());
//                    earthquakeShelterEntity.setXcord(data.getXcord());
//                    earthquakeShelterEntity.setYcord(data.getYcord());
//
//                    earthquakeShelterRepository.save(earthquakeShelterEntity);
//          }

        } catch (Exception e) {
            e.printStackTrace();
        }

//        return extractedDataList;
    }
}