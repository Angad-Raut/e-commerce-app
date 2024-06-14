package com.projectx.ecommerce.common.payloads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EntityIdAndTypeDto {
    private Long entityId;
    private Integer entityType;
}
