package com.shelter.peace.shelterApi.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shelter.peace.shelterApi.entity.EarthquakeShelterData;
import com.shelter.peace.shelterApi.repository.EarthquakeShelterRepository;
import com.shelter.peace.shelterApi.service.EarthquakeShelterService;
import com.shelter.peace.shelterApi.service.dto.EarthquakeOutdoorsShelterResponse;
import com.shelter.peace.shelterApi.service.dto.Row;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EarthquakeShelterServiceImpl implements EarthquakeShelterService {

    @Value("${earthquake.shelter.url}")
    private String apiUrl;

    @Value("${api.serviceKey}")
    private String apiKey;

    private final EarthquakeShelterRepository earthquakeShelterRepository;

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

            earthquakeOutdoorsShelterResponse.getEarthquakeOutdoorsShelters().get(0).getRow().forEach(row -> {
                EarthquakeShelterData shelterData = new EarthquakeShelterData();
                shelterData.setArcd(row.getArcd());
                shelterData.setAcmdfclty_sn(row.getAcmdfcltySn());
                shelterData.setCtprvn_nm(row.getCtprvnNm());
                shelterData.setSgg_nm(row.getSggNm());
                shelterData.setVt_acmdfclty_nm(row.getVtAcmdfcltyNm());
                shelterData.setRdnmadr_cd(row.getRdnmadrCd());
                shelterData.setBdong_cd(row.getBdongCd());
                shelterData.setHdong_cd(row.getHdongCd());
                shelterData.setDtl_adres(row.getDtlAdres());
                // 나머지 필드들도 setter를 호출하여 값을 설정합니다.


                earthquakeShelterRepository.save(shelterData);
            });


            List<Row> rows = earthquakeOutdoorsShelterResponse.getEarthquakeOutdoorsShelters().get(0).getRow();
            List<Row> nonNullRows = extractNonNullRows(rows);

            // nonNullRows에는 null이 아닌 데이터만 포함

            for (Row row : rows) {
                // 행(row)에서 null이 아닌 데이터를 처리하고 저장
                String arcd = row.getArcd();
                String acmdfcltySn = row.getAcmdfcltySn();
                String ctprvnNm = row.getCtprvnNm();
                String sggNm = row.getSggNm();
                String vtAcmdfcltyNm = row.getVtAcmdfcltyNm();
                String rdnmadrCd = row.getRdnmadrCd();
                String bdongCd = row.getBdongCd();
                String hdongCd = row.getHdongCd();
                String dtlAdres = row.getDtlAdres();
                double fcltyAr = row.getFcltyAr();
                double xcord = row.getXcord();
                double ycord = row.getYcord();

                // 데이터베이스에 저장
                EarthquakeShelterData shelterData = new EarthquakeShelterData();
                shelterData.setArcd(arcd);
                shelterData.setAcmdfclty_sn(acmdfcltySn);
                shelterData.setCtprvn_nm(ctprvnNm);
                shelterData.setSgg_nm(sggNm);
                shelterData.setVt_acmdfclty_nm(vtAcmdfcltyNm);
//                shelterData.setRdnmadr_cd(rdnmadrCd);
                shelterData.setBdong_cd(bdongCd);
                shelterData.setHdong_cd(hdongCd);
                shelterData.setDtl_adres(dtlAdres);
                shelterData.setFclty_ar(String.valueOf(fcltyAr));
                shelterData.setXcord(String.valueOf(xcord));
                shelterData.setYcord(String.valueOf(ycord));

                earthquakeShelterRepository.save(shelterData); // 데이터 저장
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private List<Row> extractNonNullRows(List<Row> rows) {
        List<Row> nonNullRows = new ArrayList<>();

        if (rows == null) {
            return nonNullRows; // rows가 null이면 빈 리스트를 반환
        }

        for (Row row : rows) {
            if (row != null
                    && row.getArcd() != null
                    && row.getAcmdfcltySn() != null
                    && row.getCtprvnNm() != null
                    && row.getSggNm() != null
                    && row.getVtAcmdfcltyNm() != null
//                    && row.getRdnmadrCd() != null
                    && row.getBdongCd() != null
                    && row.getHdongCd() != null
                    && row.getDtlAdres() != null
                    && row.getFcltyAr() != 0.0
                    && row.getXcord() != 0.0
                    && row.getYcord() != 0.0) {
                nonNullRows.add(row);
            }
        }

        return nonNullRows;
    }

}