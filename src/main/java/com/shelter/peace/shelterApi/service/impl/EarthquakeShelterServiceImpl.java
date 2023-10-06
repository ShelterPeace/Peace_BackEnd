package com.shelter.peace.shelterApi.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shelter.peace.shelterApi.entity.EarthquakeShelter;
import com.shelter.peace.shelterApi.repository.EarthquakeShelterRepository;
import com.shelter.peace.shelterApi.service.EarthquakeShelterService;
import com.shelter.peace.shelterApi.service.dto.EarthquakeShelterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class EarthquakeShelterServiceImpl implements EarthquakeShelterService {

    @Value("${earthquake.shelter.url}")
    private String apiUrl;

    @Value("${api.serviceKey}")
    private String apiKey;

    @Autowired
    private EarthquakeShelterRepository earthquakeShelterRepository;

    @Override
    public List<EarthquakeShelterDTO> extractEarthquakeShelterData() throws JsonProcessingException {
        List<EarthquakeShelterDTO> extractedDataList = new ArrayList<>();

        // API 호출을 위한 URL 생성
        String url = apiUrl + "?serviceKey=" + apiKey+"&pageNo=1" + "&numOfRows=1000" + "&type=json";

        // RestTemplate 객체 생성
        RestTemplate restTemplate = new RestTemplate();

        // API 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // HttpEntity 객체 생성하여 요청 데이터와 헤더 설정
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // RestTemplate을 사용하여 GET 요청 보내기
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        // ObjectMapper를 사용하여 JSON 응답을 객체로 매핑
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        EarthquakeShelterDTO[] responseDtoArray = objectMapper.readValue(response.getBody(), EarthquakeShelterDTO[].class);

        // 배열을 리스트로 변환
        List<EarthquakeShelterDTO> dataList = List.of(responseDtoArray);

        // 필요한 정보 추출하여 리스트에 추가
        for (EarthquakeShelterDTO data : dataList) {
            EarthquakeShelterDTO extractedData = EarthquakeShelterDTO.builder()
                    .arcd(data.getArcd())
                    .acmdfclty_sn(data.getAcmdfclty_sn())
                    .ctprvn_nm(data.getCtprvn_nm())
                    .sgg_nm(data.getSgg_nm())
                    .vt_acmdfclty_nm(data.getVt_acmdfclty_nm())
                    .rdnmadr_cd(data.getRdnmadr_cd())
                    .bdong_cd(data.getBdong_cd())
                    .hdong_cd(data.getHdong_cd())
                    .dtl_adres(data.getDtl_adres())
                    .fclty_ar(data.getFclty_ar())
                    .xcord(data.getXcord())
                    .ycord(data.getYcord())
                    .build();

            extractedDataList.add(extractedData);
        }

        // 필요한 정보 추출하여 엔티티에 저장
        for (EarthquakeShelterDTO data : dataList) {
            EarthquakeShelter existingEntity = earthquakeShelterRepository.findByArcd(data.getArcd());

            if (existingEntity == null) {
                EarthquakeShelter earthquakeShelterEntity = new EarthquakeShelter();
                earthquakeShelterEntity.setArcd(data.getArcd());
                earthquakeShelterEntity.setAcmdfclty_sn(String.valueOf(data.getAcmdfclty_sn()));
                earthquakeShelterEntity.setCtprvn_nm(data.getCtprvn_nm());
                earthquakeShelterEntity.setSgg_nm(data.getSgg_nm());
                earthquakeShelterEntity.setVt_acmdfclty_nm(data.getVt_acmdfclty_nm());
                earthquakeShelterEntity.setRdnmadr_cd(data.getRdnmadr_cd());
                earthquakeShelterEntity.setBdong_cd(data.getBdong_cd());
                earthquakeShelterEntity.setHdong_cd(data.getHdong_cd());
                earthquakeShelterEntity.setDtl_adres(data.getDtl_adres());
                earthquakeShelterEntity.setFclty_ar(data.getFclty_ar());
                earthquakeShelterEntity.setXcord(data.getXcord());
                earthquakeShelterEntity.setYcord(data.getYcord());

                earthquakeShelterRepository.save(earthquakeShelterEntity); // 엔티티 저장
            }
        }

        return extractedDataList; // 필요한 정보가 담긴 DTO 리스트 반환
    }
}
