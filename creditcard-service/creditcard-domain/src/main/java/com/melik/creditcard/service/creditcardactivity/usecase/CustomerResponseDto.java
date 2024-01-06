package com.melik.creditcard.service.creditcardactivity.usecase;

import com.melik.creditcard.service.common.valueobject.StatusType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Author mselvi
 * @Created 04.01.2024
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponseDto {

    private Long id;
    private String firstName;
    private String lastName;
    private Long identityNo;
    private LocalDateTime cancelDate;
    private StatusType statusType;
}
