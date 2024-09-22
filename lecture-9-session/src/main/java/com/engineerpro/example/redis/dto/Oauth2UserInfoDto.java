package com.engineerpro.example.redis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Oauth2UserInfoDto {

    private String name;
    private String email;
    private String picture;
    private String id;

}
