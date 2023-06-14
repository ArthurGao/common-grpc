package com.arthur.grpc.aop;

import com.arthur.grpc.annotations.GrpcRequestValidation;
import java.util.Map;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
@Log4j2
public abstract class GrpcServiceValidationAspect extends GrpcAspectBase {

  @Around("@annotation(grpcRequestValidation)")
  public Object handle(ProceedingJoinPoint joinPoint, GrpcRequestValidation grpcRequestValidation) throws Throwable {
    if (!isGrpcMethod(joinPoint)) {
      throw new UnsupportedOperationException(
          String.format("GrpcRequestValidation annotation can be only used in gRPC method. Please check %s.", joinPoint.getSignature().getName()));
    }
    Map<String, Object> params = getNameAndValue(joinPoint);
    for (Map.Entry<String, Object> entry : params.entrySet()) {
      if (entry.getKey().contains("responseObserver")) {
        continue;
      }
      validateGrpcRequest(entry.getValue());
    }
    return joinPoint.proceed();
  }

  public abstract void validateGrpcRequest(Object grpcRequest);
}
