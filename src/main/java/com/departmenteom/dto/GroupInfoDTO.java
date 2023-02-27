package com.departmenteom.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class GroupInfoDTO {
    private Long id;
    private String groupCipher;
    private Long groupCipherId;
    private LocalDateTime admissionYear;
    private int index;
    private String groupFullName;
    private Long streamId;
}
