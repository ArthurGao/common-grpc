package com.arthur.grpc.aop;


import com.arthur.grpc.exception.BadRequestException;
import com.arthur.grpc.exception.DataNotFoundException;
import com.arthur.grpc.exception.ServerInternalException;
import com.arthur.test.grpc.TestRequest;
import org.junit.platform.commons.util.StringUtils;

public class GrpcServiceValidationAspectImpl extends GrpcServiceValidationAspect {

  @Override
  public void validateGrpcRequest(Object grpcRequest) {
    if (grpcRequest instanceof TestRequest) {
      TestRequest request = (TestRequest) grpcRequest;
      if (request.getLongRequest() < 0) {
        throw new BadRequestException("LongRequest must be positive");
      }
      if (StringUtils.isBlank(request.getStringRequest())) {
        throw new BadRequestException("StringRequest must not be blank");
      }
      if (request.getStringRequest().equals("ServerInternalException")) {
        throw new ServerInternalException("Throw ServerInternalException");
      }
      if (request.getStringRequest().equals("DataNotFoundException")) {
        throw new DataNotFoundException("Throw DataNotFoundException");
      }
      if (request.getStringRequest().equals("NullPointerException")) {
        throw new NullPointerException("Throw NullPointerException");
      }
      if (request.getStringRequest().equals("IllegalArgumentException")) {
        throw new IllegalArgumentException("Throw IllegalArgumentException");
      }
    }
  }
}
