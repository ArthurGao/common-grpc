package com.arthur.grpc.exception;

import lombok.Getter;

/**
 * `BadRequestException` is a {@link RuntimeException} that is thrown when a client sends a bad request. It is mapped to gRPC {@link
 * io.grpc.StatusRuntimeException} with status code {@see com.google.rpc.Code.BAD_REQUEST}
 */
public class BadRequestException extends RuntimeException {

  @Getter
  private final GrpcErrorCode grpcErrorCode;

  public BadRequestException() {
    super();
    this.grpcErrorCode = GrpcErrorCode.BAD_REQUEST;
  }


  public BadRequestException(String message) {
    super(message);
    this.grpcErrorCode = GrpcErrorCode.BAD_REQUEST;
    this.grpcErrorCode.setMessage(message);
  }

  public BadRequestException(String message, GrpcErrorCode grpcErrorCode,
      Throwable cause) {
    super(message, cause);
    this.grpcErrorCode = grpcErrorCode;
  }
}
