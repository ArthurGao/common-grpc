package com.arthur.grpc.aop;

import io.grpc.stub.StreamObserver;
import java.util.HashMap;
import java.util.Map;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.CodeSignature;

public class GrpcAspectBase {

  public Map<String, Object> getNameAndValue(ProceedingJoinPoint joinPoint) {
    Map<String, Object> param = new HashMap<>();
    Object[] paramValues = joinPoint.getArgs();
    String[] paramNames = ((CodeSignature) joinPoint.getSignature()).getParameterNames();
    for (int i = 0; i < paramNames.length; i++) {
      param.put(paramNames[i], paramValues[i]);
    }
    return param;
  }

  public boolean isGrpcMethod(ProceedingJoinPoint joinPoin) {
    Object[] params = joinPoin.getArgs();
    return params.length == 2 && (params[1] instanceof StreamObserver);
  }
}
