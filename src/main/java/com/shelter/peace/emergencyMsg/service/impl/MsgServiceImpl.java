package com.shelter.peace.emergencyMsg.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shelter.peace.emergencyMsg.entity.DisasterMsg;
import com.shelter.peace.emergencyMsg.repository.MsgRepository;
import com.shelter.peace.emergencyMsg.service.MsgService;
import com.shelter.peace.emergencyMsg.service.dto.DisasterMsgDTO;
import com.shelter.peace.emergencyMsg.service.dto.DisasterMsgData;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MsgServiceImpl implements MsgService {
    @Value("${api.disasterMsg.url}")
    private String apiUrl;

    @Value("${api.serviceKey}")
    private String apiKey;

    private final MsgRepository msgRepository;

    @Override
    public void extractDisasterMsgData() {

        // API 호출을 위한 URL 생성
        String url = apiUrl + "?ServiceKey=" + apiKey + "&type=json&pageNo=1&numOfRows=100";

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

            // JSON 응답 파싱
            ObjectMapper objectMapper = new ObjectMapper();
            DisasterMsgData disasterMsgData = objectMapper.readValue(responseBody, DisasterMsgData.class);

            System.out.println("aaaaaaaaaa");
            System.out.println(disasterMsgData.getDisasterMsg());


            if (disasterMsgData.getDisasterMsg() != null) {
                List<DisasterMsgData.Row> rows = disasterMsgData.getDisasterMsg().get(1).getRow();
                if (rows != null) {
                    for (DisasterMsgData.Row rowData : rows) {
                        System.out.println("bbbbbbbb");

                        // "location_id" 처리
                        String locationId = rowData.getLocationId();
                        String[] locationIds = locationId.split(",");

                        for (String singleLocationId : locationIds) {
                            int locationIdProcessed = Integer.parseInt(singleLocationId);

                            DisasterMsgDTO disasterMsgDTO = new DisasterMsgDTO();
                            disasterMsgDTO.setCreateDate(rowData.getCreateDate());
                            disasterMsgDTO.setLocationId(locationIdProcessed);
                            disasterMsgDTO.setLocationName(rowData.getLocationName());
                            disasterMsgDTO.setMd101Sn(Integer.parseInt(String.valueOf(rowData.getMd101Sn())));
                            disasterMsgDTO.setMessage(rowData.getMessage());
                            disasterMsgDTO.setSendPlatform(rowData.getSendPlatform());

                            System.out.println("cccccccc");

                            // 데이터를 저장소에 저장
                            DisasterMsg disasterMsg = new DisasterMsg();
                            disasterMsg.setCreateDate(disasterMsgDTO.getCreateDate());
                            disasterMsg.setLocationId(String.valueOf(disasterMsgDTO.getLocationId()));
                            disasterMsg.setLocationName(disasterMsgDTO.getLocationName());
                            disasterMsg.setMd101Sn(disasterMsgDTO.getMd101Sn());
                            disasterMsg.setMessage(disasterMsgDTO.getMessage());
                            disasterMsg.setSendPlatform(disasterMsgDTO.getSendPlatform());

                            msgRepository.save(disasterMsg);
                        }
                    }
                }
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}






