package com.shelter.peace.fireExtinguisher.service;

import com.shelter.peace.fireExtinguisher.entity.FireExtinguisher;
import com.shelter.peace.fireExtinguisher.entity.GyeonggiFireExt;
import com.shelter.peace.fireExtinguisher.repository.GyeonggiFireExtRepository;
import com.shelter.peace.fireExtinguisher.service.dto.GyeonggiFireExtDTO;
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
public class GyeonggiFireExtService {
    @Value("${api.serviceKey3}")
    private String apiKey;

    @Value("${api.fire.url2}")
    private String apiUrl;

    private final GyeonggiFireExtRepository gyeonggiFireExtRepository;
    public void fetchGyeonggiFireExtData() {
        //// 총 데이터 량 31205 개라서  여러번 돌려야 함
        String url = apiUrl + "?Key=" + apiKey + "&type=xml&pIndex=1&pSize=100";

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        try {
            // API 호출 및 응답 받기
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
            String responseBody = responseEntity.getBody();

            // XML 응답을 DTO 객체로 변환
            JAXBContext jaxbContext = JAXBContext.newInstance(GyeonggiFireExtDTO.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            StringReader reader = new StringReader(responseBody);
            GyeonggiFireExtDTO gyeonggiFireExtResponse = (GyeonggiFireExtDTO) unmarshaller.unmarshal(reader);

            // GyeonggiFireExt 정보 저장
            for (GyeonggiFireExtDTO.Row row : gyeonggiFireExtResponse.getRow()) {
                // facLtNo를 기준으로 중복 확인
                if (gyeonggiFireExtRepository.findByRefineLotnoAddr(row.getRefineLotnoAddr()).isEmpty()) {
                    GyeonggiFireExt gyeonggiFireExt = new GyeonggiFireExt();

                    gyeonggiFireExt.setSigunNm(row.getSigunNm());
                    gyeonggiFireExt.setFacLtDeviceDetailDivNm(row.getFacLtDeviceDetailDivNm());
                    gyeonggiFireExt.setRefineWgs84Lat(row.getRefineWgs84Lat());
                    gyeonggiFireExt.setRefineWgs84Logt(row.getRefineWgs84Logt());
                    gyeonggiFireExt.setJurisdFiresttnNm(row.getJurisdFiresttnNm());
                    gyeonggiFireExt.setJurisdFiresttnTelNo(row.getJurisdFiresttnTelNo());
                    gyeonggiFireExt.setFacLtTypeCd(row.getFacLtTypeCd());
                    gyeonggiFireExt.setUsePosblYn(row.getUsePosblYn());
                    gyeonggiFireExt.setSigunCd(row.getSigunCd());
                    gyeonggiFireExt.setSidoNm(row.getSidoNm());
                    gyeonggiFireExt.setFacLtNo(row.getFacLtNo());
                    gyeonggiFireExt.setRefineLotnoAddr(row.getRefineLotnoAddr());
                    gyeonggiFireExt.setRefineRoadNmAddr(row.getRefineRoadNmAddr());
                    gyeonggiFireExt.setInstNm(row.getInstNm());

                    gyeonggiFireExtRepository.save(gyeonggiFireExt);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public Page<GyeonggiFireExt> getGyeonggiFireExtList(Pageable pageable) {
        return gyeonggiFireExtRepository.findAll(pageable);
    }
}
