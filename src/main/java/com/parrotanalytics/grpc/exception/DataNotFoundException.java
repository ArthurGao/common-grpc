package com.arthur.grpc.exception;

import lombok.Getter;

/**
 * `DataNotFoundException` is a {@link RuntimeException} that is thrown when a data is not found. It is mapped to gRPC {@link io.grpc.StatusRuntimeException}
 * with status code {@see com.google.rpc.Code.NOT_FOUND}
 */
public class DataNotFoundException extends RuntimeException {

  @Getter
  private final GrpcErrorCode grpcErrorCode;

  public DataNotFoundException() {
    super();
    this.grpcErrorCode = GrpcErrorCode.DATA_NOT_FOUND;
  }

  public DataNotFoundException(String msg) {
    super(msg);
    this.grpcErrorCode = GrpcErrorCode.DATA_NOT_FOUND;
    this.grpcErrorCode.setMessage(msg);
  }

  public DataNotFoundException(String msg, GrpcErrorCode grpcErrorCode, Throwable t) {
    super(msg, t);
    this.grpcErrorCode = grpcErrorCode;
    this.grpcErrorCode.setMessage(msg);
  }
}

