package com.shelter.peace.inquiry.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
public class QnABoardDTO {
    private Long qnANo;
    private String qnATitle;
    private int qnACnt;
    private LocalDateTime createdDate;
    private String qnAWriter;
}
