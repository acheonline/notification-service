package ru.achernyavskiy0n.notificationservice.controller;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Builder
@Data
@Accessors
public class ApiResponse {

  private boolean success;
  private int status;
  private String message;
}
