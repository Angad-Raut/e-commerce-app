package com.projectx.ecommerce.common.payloads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EntityTypeAndValueDto {
    private Integer entityType;
    private Double entityValue;
}
