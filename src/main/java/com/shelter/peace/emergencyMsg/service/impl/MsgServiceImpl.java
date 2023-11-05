package com.shelter.peace.emergencyMsg.service.impl;

import com.shelter.peace.emergencyMsg.entity.DisasterMsg;
import com.shelter.peace.emergencyMsg.repository.MsgRepository;
import com.shelter.peace.emergencyMsg.service.MsgService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;

@Service
@RequiredArgsConstructor
public class MsgServiceImpl implements MsgService {
    @Value("${api.disasterMsg.url}")
    private String apiUrl;

    @Value("${api.serviceKey}")
    private String apiKey;

    private final MsgRepository msgRepository;
    private final KeywordService keywordService;

    @Override
    public boolean extractDisasterMsgData() {

        // API 호출을 위한 URL 생성
        String url = apiUrl + "?ServiceKey=" + apiKey + "&type=xml&pageNo=1&numOfRows=10";

        RestTemplate restTemplate = new RestTemplate();

        try {
            // API 호출 및 응답 받기
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_XML);

            ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
            String responseBody = responseEntity.getBody();

            // XML 파싱
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(responseBody));
            Document document = builder.parse(is);

            NodeList rowNodes = document.getElementsByTagName("row");
            for (int i = 0; i < rowNodes.getLength(); i++) {
                Element rowElement = (Element) rowNodes.item(i);

                // 데이터 추출
                String createDate = rowElement.getElementsByTagName("create_date").item(0).getTextContent();
                String locationId = rowElement.getElementsByTagName("location_id").item(0).getTextContent();
                String locationName = rowElement.getElementsByTagName("location_name").item(0).getTextContent();
                int md101Sn = Integer.parseInt(rowElement.getElementsByTagName("md101_sn").item(0).getTextContent());
                String message = rowElement.getElementsByTagName("msg").item(0).getTextContent();
                String sendPlatform = rowElement.getElementsByTagName("send_platform").item(0).getTextContent();

                // KeywordService를 사용하여 메시지 처리
                String processedSentence = keywordService.processMessageForKeywords(message);

                if (processedSentence != null) {
                    System.out.println(processedSentence);
                    // 추후 추가적으로 로그인한 유저의 관련엔티티에 컬럼 추가로 만들어서 그안에 저장 예정
                }


                // 데이터를 저장소에 저장 (중복 방지 처리 포함)
                saveOrUpdateDisasterMsg(createDate, locationId, locationName,
                        md101Sn, message, sendPlatform);
            }
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    private void saveOrUpdateDisasterMsg(String createDate, String locationId, String locationName, int md101Sn, String message, String sendPlatform) {
        // 동일한 속성을 가진 레코드가 이미 존재하는지 확인
        boolean recordExists = msgRepository.existsByCreateDateAndLocationIdAndLocationNameAndMd101SnAndMessageAndSendPlatform(
                createDate, locationId, locationName, md101Sn, message, sendPlatform
        );

        // 레코드가 존재하지 않는 경우 저장
        if (!recordExists) {
            DisasterMsg disasterMsg = new DisasterMsg();
            disasterMsg.setCreateDate(createDate);
            disasterMsg.setLocationId(locationId);
            disasterMsg.setLocationName(locationName);
            disasterMsg.setMd101Sn(md101Sn);
            disasterMsg.setMessage(message);
            disasterMsg.setSendPlatform(sendPlatform);

            msgRepository.save(disasterMsg);
        }
        // 이미 레코드가 존재하는 경우 아무 작업 없음
    }
}






