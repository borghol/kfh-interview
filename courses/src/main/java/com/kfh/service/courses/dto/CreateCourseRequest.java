package com.kfh.service.courses.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@Builder
public class CreateCourseRequest {
  @NotBlank
  @Size(min = 6, max = 6)
  String name;
  @Valid
  String userId;
}
