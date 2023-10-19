package com.shelter.peace.sms.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageDTO {
    String to;
    String content;
}
