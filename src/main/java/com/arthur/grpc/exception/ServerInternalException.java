package com.arthur.grpc.exception;

import lombok.Getter;

/**
 * `ServerInternalException` is a {@link RuntimeException} that is thrown when the server encounters an internal error. It is mapped to gRPC {@link
 * io.grpc.StatusRuntimeException} with status code {@see com.google.rpc.Code.INTERNAL}
 */
public class ServerInternalException extends RuntimeException {

  @Getter
  private final GrpcErrorCode grpcErrorCode;

  public ServerInternalException() {
    super();
    this.grpcErrorCode = GrpcErrorCode.INTERNAL_ERROR;
  }

  public ServerInternalException(String msg) {
    super(msg);
    this.grpcErrorCode = GrpcErrorCode.INTERNAL_ERROR;
    this.grpcErrorCode.setMessage(msg);
  }

  public ServerInternalException(String msg, GrpcErrorCode grpcErrorCode, Throwable t) {
    super(msg, t);
    this.grpcErrorCode = grpcErrorCode;
    this.grpcErrorCode.setMessage(msg);
  }
}
