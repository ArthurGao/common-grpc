package com.arthur.grpc.aop.handler;

import com.google.protobuf.Any;
import com.google.rpc.Code;
import com.google.rpc.ErrorInfo;
import com.google.rpc.Status;
import com.arthur.grpc.exception.BadRequestException;
import com.arthur.grpc.exception.DataNotFoundException;
import com.arthur.grpc.exception.GrpcErrorCode;
import com.arthur.grpc.exception.ServerInternalException;
import io.grpc.StatusRuntimeException;
import io.grpc.protobuf.StatusProto;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;

@GrpcAdvice
@Log4j2
public class ExceptionHandler {

  private static final String ERROR_DOMAIN = "com.arthur.platformsync.service.error";
  private static final String META_HEADER = "Error meta data";

  @GrpcExceptionHandler(ServerInternalException.class)
  public StatusRuntimeException handleServerInternalException(ServerInternalException cause) {
    log.warn("Caught server internal exception: ", cause);

    return getStatusRuntimeException(cause.getGrpcErrorCode());
  }

  @GrpcExceptionHandler(BadRequestException.class)
  public StatusRuntimeException handleInvalidArgumentException(BadRequestException cause) {
    log.warn("Caught invalid argument exception: ", cause);

    return getStatusRuntimeException(cause.getGrpcErrorCode());
  }

  @GrpcExceptionHandler(DataNotFoundException.class)
  public StatusRuntimeException handleDataNotFoundException(DataNotFoundException cause) {
    log.warn("Caught invalid argument exception: ", cause);

    return getStatusRuntimeException(cause.getGrpcErrorCode());
  }

  @GrpcExceptionHandler(IllegalArgumentException.class)
  public StatusRuntimeException handleIllegalArgumentException(IllegalArgumentException cause) {
    log.warn("Caught illegal argument exception", cause);
    return getStatusRuntimeException(cause.getLocalizedMessage(), Code.INVALID_ARGUMENT);
  }

  @GrpcExceptionHandler(RuntimeException.class)
  public StatusRuntimeException handleRuntimeException(RuntimeException cause) {
    log.warn("Caught runtime exception", cause);
    //For all Java Runtime exception, return Internal error code amd generic internal error message
    return getStatusRuntimeException(cause.getLocalizedMessage(), Code.INTERNAL);
  }

  private StatusRuntimeException getStatusRuntimeException(GrpcErrorCode grpcErrorCode) {
    return getStatusRuntimeException(grpcErrorCode.getMessage(), grpcErrorCode.getErrorCode());
  }

  private StatusRuntimeException getStatusRuntimeException(String localizedMessage, Code invalidArgument) {
    ErrorInfo errorInfo =
        ErrorInfo.newBuilder()
            .setReason(localizedMessage)
            .setDomain(ERROR_DOMAIN)
            .putMetadata(META_HEADER, localizedMessage)
            .build();
    Status status =
        Status.newBuilder()
            .setCode(invalidArgument.getNumber())
            .setMessage(errorInfo.getReason())
            .addDetails(Any.pack(errorInfo))
            .build();
    return StatusProto.toStatusRuntimeException(status);
  }
}

