package com.projectx.ecommerce.authentication.payloads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponseDto {
    private String userName;
    private Long userMobile;
    private String userEmail;
    private Boolean isAdmin;
}
