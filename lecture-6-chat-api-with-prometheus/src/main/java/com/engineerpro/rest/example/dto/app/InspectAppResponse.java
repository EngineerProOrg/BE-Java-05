package com.engineerpro.rest.example.dto.app;

import com.engineerpro.rest.example.model.App;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class InspectAppResponse {
  private App app;
}
