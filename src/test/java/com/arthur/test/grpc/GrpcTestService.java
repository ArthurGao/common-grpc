package com.arthur.test.grpc;

import com.arthur.grpc.annotations.GrpcRequestValidation;
import com.arthur.grpc.annotations.GrpcServiceLog;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.server.service.GrpcService;

@Log4j2
@GrpcService
public class GrpcTestService extends GrpcTestServiceGrpc.GrpcTestServiceImplBase {

  @Override
  @GrpcServiceLog
  @GrpcRequestValidation
  public void testCallService(TestRequest request,
      io.grpc.stub.StreamObserver<TestResponse> responseObserver) {
    TestResponse testResponse = TestResponse.newBuilder().setStringResponse(request.getStringRequest()).setLongResponse(request.getLongRequest())
        .build();
    responseObserver.onNext(testResponse);
    responseObserver.onCompleted();
  }

  @Override
  public void testCallServiceNoAnnotation(com.arthur.test.grpc.TestRequest request,
      io.grpc.stub.StreamObserver<com.arthur.test.grpc.TestResponse> responseObserver) {
    TestResponse testResponse = TestResponse.newBuilder().setStringResponse(request.getStringRequest()).setLongResponse(request.getLongRequest())
        .build();
    responseObserver.onNext(testResponse);
    responseObserver.onCompleted();
  }
  
  @GrpcServiceLog
  public void testCallNoneGrpcServiceForLog() {
  }

  @GrpcRequestValidation
  public void testCallNoneGrpcServiceForValidation() {
  }

}
