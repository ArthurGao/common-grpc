package com.arthur.grpc.aop;

import com.arthur.grpc.annotations.GrpcServiceLog;
import java.util.Map;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
@Log4j2
public class GrpcServiceLogAspect extends GrpcAspectBase {

  @Around("@annotation(grpcServiceLog)")

  public Object handle(ProceedingJoinPoint joinPoint, GrpcServiceLog grpcServiceLog) throws Throwable {
    String serviceName = joinPoint.getSignature().getName();
    if (!isGrpcMethod(joinPoint)) {
      throw new UnsupportedOperationException(String.format("GrpcServiceLog annotation can be only used in gRPC method. Please check %s.", serviceName));
    }

    log.info("[gRPC start], serviceName->{} \n", serviceName);
    Map<String, Object> params = getNameAndValue(joinPoint);
    int index = 0;
    for (Map.Entry<String, Object> entry : params.entrySet()) {
      if (entry.getKey().contains("responseObserver")) {
        continue;
      }
      log.info(
          "[gRPC {}] [name] {} [value] {} \n", index, entry.getKey(), entry.getValue()
              .toString().replace("\n", " "));

      index++;
    }

    long begin = System.currentTimeMillis();
    Object result = joinPoint.proceed();

    log.info("[gRPC end], serviceName->{}, Time consumed->{}ms \n",
        serviceName, System.currentTimeMillis() - begin);
    return result;
  }
}
