package com.shelter.peace.sms.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SmsRequestDTO {
    String type;
    String contentType;
    String countryCode;
    String from;
    String content;
    List<MessageDTO> messages;
}
