package com.shelter.peace.sms.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shelter.peace.sms.dto.MessageDTO;
import com.shelter.peace.sms.dto.SmsRequestDTO;
import com.shelter.peace.sms.dto.SmsResponseDTO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SmsService {
    @Value("${ncp.sms.service.id}")
    private String serviceId;
    @Value("${ncp.access.key}")
    private String accessKey;
    @Value("${ncp.secret.key}")
    private String secretKey;
    @Value("${phone.number}")
    private String adminPhone;
    private String sensUrl = "https://sens.apigw.ntruss.com/sms/v2/services/" + serviceId + "/messages";

    private final String PREFIX = "sms:";

    private final StringRedisTemplate redisTemplate;

    public SmsResponseDTO sendSms(MessageDTO messageDTO) {
        try {
            Long time = System.currentTimeMillis();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("x-ncp-apigw-timestamp", time.toString());
            headers.set("x-ncp-iam-access-key", accessKey);
            headers.set("x-ncp-apigw-signature-v2", makeSignature(time));

            List<MessageDTO> messages = new ArrayList<>();
            messages.add(messageDTO);

            SmsRequestDTO request = SmsRequestDTO.builder()
                    .type("sms")
                    .from(adminPhone)
                    .content(messageDTO.getContent())
                    .messages(messages)
                    .build();

            ObjectMapper objectMapper = new ObjectMapper();
            String body = objectMapper.writeValueAsString(request);
            HttpEntity<String> httpBody = new HttpEntity<>(body, headers);

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

            SmsResponseDTO response = restTemplate.postForObject(new URI(sensUrl), httpBody, SmsResponseDTO.class);
            System.out.println(response);
            return response;
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    public String makeSignature(Long time) {
        try {
            String space = " ";
            String newLine = "\n";
            String method = "POST";
            String url = sensUrl.substring(sensUrl.indexOf(".com") + 4);
            String timestamp = time.toString();

            String message = new StringBuilder()
                    .append(method)
                    .append(space)
                    .append(url)
                    .append(newLine)
                    .append(timestamp)
                    .append(newLine)
                    .append(this.accessKey)
                    .toString();

            SecretKeySpec signingKey = new SecretKeySpec(this.secretKey.getBytes("UTF-8"), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);

            byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
            String encodeBase64String = Base64.encodeBase64String(rawHmac);

            return encodeBase64String;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    //문자 내용 만들기
    public String createSmsContent(String certificationNumber) {
        String builder = new StringBuilder()
                .append("[대피스] 인증번호는 ")
                .append(certificationNumber)
                .append("입니다.")
                .toString();

        return builder;
    }

    //인증번호 만들기
    public String createRandomNumber() {
        int certificationNumber = (int)(Math.random() * 1000000);
        return String.valueOf(certificationNumber);
    }

    //사용자에게 인증번호 문자 보내기
    public void sendAuthSms(String phone) {
        String randomNumber = createRandomNumber();
        String content = createSmsContent(randomNumber);

        MessageDTO messageDTO = MessageDTO.builder()
                .to(phone)
                .content(content)
                .build();

        sendSms(messageDTO);

        setCertification(phone, randomNumber);
    }

    //인증번호 검증하기
    public void verifySms(String phone, String certificationNumber) {
        if (!isVerify(phone, certificationNumber)) {
            throw new RuntimeException("인증번호가 일치하지 않습니다.");
        }
        deleteCertification(phone);
    }

    //인증번호 검증하기
    private boolean isVerify(String phone, String certificationNumber) {
        return hasCertification(phone) &&
                getCertification(phone).equals(certificationNumber);
    }


    /* 레디스 연동 부분*/

    //휴대전화, 인증번호 레디스 저장
    public void setCertification(String phone, String certificationNumber) {
        redisTemplate.opsForValue()
                .set(PREFIX + phone, certificationNumber, Duration.ofSeconds(180));
    }

    //인증번호 추출
    public String getCertification(String phone) {
        return redisTemplate.opsForValue().get(PREFIX + phone);
    }

    public void deleteCertification(String phone) {
        redisTemplate.delete(PREFIX + phone);
    }

    public boolean hasCertification(String phone) {
        return redisTemplate.hasKey(PREFIX + phone);
    }


}
