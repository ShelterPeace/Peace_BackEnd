package com.shelter.peace.fireExtinguisher.service;

import com.shelter.peace.fireExtinguisher.entity.FireExtinguisher;
import com.shelter.peace.fireExtinguisher.repository.FireExtinguisherRepository;
import com.shelter.peace.fireExtinguisher.service.dto.FireExtinguisherDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class FireExtinguisherService {
    @Value("${api.serviceKey2}")
    private String apiKey;

    @Value("${api.fire.url}")
    private String apiUrl;

    private final FireExtinguisherRepository fireExtinguisherRepository;
    public void fetchFireExtinguisherData() {
        // 총 데이터 량 2694 개라서 여러번 돌려야 함
        String url = apiUrl + apiKey + "/xml/TbGiEmerhydP/1/1000";

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        try {
            // API 호출 및 응답 받기
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
            String responseBody = responseEntity.getBody();

            // XML 응답을 DTO 객체로 변환
            JAXBContext jaxbContext = JAXBContext.newInstance(FireExtinguisherDTO.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            StringReader reader = new StringReader(responseBody);
            FireExtinguisherDTO fireExtinguisherResponse = (FireExtinguisherDTO) unmarshaller.unmarshal(reader);

            // FireExtinguisher 정보 저장
            for (FireExtinguisherDTO.Row row : fireExtinguisherResponse.getRows()) {
                if (fireExtinguisherRepository.findById(row.getEmerHydSeq()).isEmpty()) {
                    FireExtinguisher fireExtinguisher = new FireExtinguisher();
                    fireExtinguisher.setId(row.getEmerHydSeq());
                    fireExtinguisher.setObjectId(row.getObjectId());
                    fireExtinguisher.setEmerHydSeq(row.getEmerHydSeq());
                    fireExtinguisher.setGisId(row.getGisId());
                    fireExtinguisher.setNaviX(row.getNaviX());
                    fireExtinguisher.setNaviY(row.getNaviY());
                    fireExtinguisher.setEmerHydNo(row.getEmerHydNo());
                    fireExtinguisher.setUseYnSe(row.getUseYnSe());
                    fireExtinguisher.setDeleteAt(row.getDeleteAt());
                    fireExtinguisher.setUpdtDe(row.getUpdtDe());

                    fireExtinguisherRepository.save(fireExtinguisher);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Page<FireExtinguisher> getFireExtinguisherList(Pageable pageable) {
        return fireExtinguisherRepository.findAll(pageable);
    }
}
