package com.shelter.peace.aedApi.service;

import com.shelter.peace.aedApi.entity.AedEntity;
import com.shelter.peace.aedApi.repository.AEDRepository;
import com.shelter.peace.aedApi.service.dto.AEDInformationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;


@Service
@RequiredArgsConstructor
public class AedApiService {
    @Value("${api.serviceKey}")
    private String apiKey;

    @Value("${api.aed.url}")
    private String apiUrl;


    private final AEDRepository aedRepository;

    public void fetchAEDData() {
        // API 호출을 위한 URL 생성
        for (int pageNo = 1; pageNo <= 5; pageNo++) {
            String url = apiUrl + "?ServiceKey=" + apiKey + "&pageNo=" + pageNo + "&numOfRows=10000";

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters()
                    .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
            try {
                // API 호출 및 응답 받기
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_XML);

                ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
                String responseBody = responseEntity.getBody();

                // XML 응답을 DTO 객체로 변환
                JAXBContext jaxbContext = JAXBContext.newInstance(AEDInformationResponse.class);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                StringReader reader = new StringReader(responseBody);
                AEDInformationResponse aedResponse = (AEDInformationResponse) unmarshaller.unmarshal(reader);

                // AED 정보 저장
                for (AEDInformationResponse.Item item : aedResponse.getBody().getItems()) {
                    String uniqueKey = item.getBuildAddress() + item.getBuildPlace();
                    if (aedRepository.findByUniqueKey(uniqueKey) == null) {
                        AedEntity aedEntity = new AedEntity();
                        aedEntity.setUniqueKey(uniqueKey);
                        aedEntity.setWgs84Lat(item.getWgs84Lat());
                        aedEntity.setWgs84Lon(item.getWgs84Lon());
                        aedEntity.setZipcode1(item.getZipcode1());
                        aedEntity.setZipcode2(item.getZipcode2());
                        aedEntity.setOrg(item.getOrg());
                        aedEntity.setManager(item.getManager());
                        aedEntity.setManagerTel(item.getManagerTel());
                        aedEntity.setMfg(item.getMfg());
                        aedEntity.setModel(item.getModel());
                        aedEntity.setBuildAddress(item.getBuildAddress());
                        aedEntity.setBuildPlace(item.getBuildPlace());
                        aedEntity.setClerkTel(item.getClerkTel());

                        aedRepository.save(aedEntity);
                    }
                }
            } catch (JAXBException e) {
                throw new RuntimeException(e);
            }
        }
    }
}