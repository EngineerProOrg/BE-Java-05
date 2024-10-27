package com.engineerpro.example.redis.dto;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class UploadImageRequest {
  @Length(min = 1)
  private String base64ImageString;
}
