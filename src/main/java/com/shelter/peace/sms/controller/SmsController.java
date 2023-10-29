package com.shelter.peace.sms.controller;

import com.shelter.peace.dto.ResponseDTO;
import com.shelter.peace.sms.service.SmsService;
import com.shelter.peace.sms.dto.MessageDTO;
import com.shelter.peace.sms.dto.SmsResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sms")
public class SmsController {
    private final SmsService smsService;

    @PostMapping("/send")
    public ResponseEntity<?> sendSms(@RequestBody MessageDTO messageDTO) {
        ResponseDTO<SmsResponseDTO> responseDTO = new ResponseDTO<>();
        try {
            System.out.println(messageDTO);
            SmsResponseDTO response = smsService.sendSms(messageDTO);
            responseDTO.setItem(response);
            responseDTO.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    //인증번호 전송
    @GetMapping("/auth/send")
    public ResponseEntity<?> sendSms(@RequestParam(value = "phone") String phone) {

        smsService.sendAuthSms(phone);

        return ResponseEntity.ok().build();
    }

    //인증번호 확인
    @PostMapping("/auth/validate")
    public ResponseEntity<?> verificationSms(@RequestBody MessageDTO messageDTO) {

        smsService.verifySms(messageDTO.getTo(), messageDTO.getContent());

        return ResponseEntity.ok().build();
    }
}
