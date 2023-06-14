package com.arthur.grpc.exception;

import com.google.rpc.Code;
import lombok.Getter;
import lombok.Setter;

public enum GrpcErrorCode {
  BAD_REQUEST(Code.INVALID_ARGUMENT, "One or more of the arguments are invalid."),
  INTERNAL_ERROR(Code.INTERNAL, "Could not process the request. Please try again later."),
  DATA_NOT_FOUND(Code.NOT_FOUND, "No data available.");

  @Getter
  private final Code errorCode;

  @Getter
  @Setter
  private String message;

  GrpcErrorCode(Code errorCode, String message) {
    this.errorCode = errorCode;
    this.message = message;
  }
}
