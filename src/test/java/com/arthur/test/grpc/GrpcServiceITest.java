package com.arthur.test.grpc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.catchThrowable;

import com.google.common.util.concurrent.ListenableFuture;
import com.arthur.grpc.config.arthurTestGrpcConfiguration;
import io.grpc.ManagedChannel;
import io.grpc.Status.Code;
import io.grpc.StatusRuntimeException;
import io.grpc.inprocess.InProcessChannelBuilder;
import java.util.concurrent.ExecutionException;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Import({arthurTestGrpcConfiguration.class})
@DirtiesContext
@Log4j2
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GrpcServiceITest {

  private ManagedChannel channel;

  @Value("${grpc.server.inProcessName}")
  private String processName;

  @Autowired
  private GrpcTestService grpcTestService;

  @BeforeAll
  public void setupChannels() {
    channel = InProcessChannelBuilder.forName(processName)
        .usePlaintext()
        .build();
  }

  @AfterAll
  public void tearDown() {
    channel.shutdown();
  }

  @Test
  void test_testCallService_givenCorrectInput_returnCorrectResponse() throws ExecutionException, InterruptedException {
    TestRequest request = TestRequest.newBuilder().setLongRequest(10L).setStringRequest("test").build();
    ListenableFuture<TestResponse> responseFuture = GrpcTestServiceGrpc
        .newFutureStub(channel).testCallService(request);
    TestResponse response = responseFuture.get();
    assertThat(response.getLongResponse()).isEqualTo(10L);
    assertThat(response.getStringResponse()).isEqualTo("test");
  }

  @Test
  void test_testCallService_givenNegativeLongRequest_returnInvalidArgument() throws ExecutionException, InterruptedException {
    TestRequest request = TestRequest.newBuilder().setLongRequest(-1).setStringRequest("test").build();
    ListenableFuture<TestResponse> responseFuture = GrpcTestServiceGrpc
        .newFutureStub(channel).testCallService(request);
    Throwable thrown = catchThrowable(responseFuture::get);
    assertThat(thrown.getCause()).isInstanceOf(StatusRuntimeException.class);
    assertThat(((StatusRuntimeException) thrown.getCause()).getStatus().getCode())
        .isEqualTo(Code.INVALID_ARGUMENT);
    assertThat(thrown.getCause().getMessage())
        .contains("LongRequest must be positive");
  }

  @Test
  void test_testCallService_givenBlankStringRequest_returnInvalidArgument() throws ExecutionException, InterruptedException {
    TestRequest request = TestRequest.newBuilder().setLongRequest(10).setStringRequest("").build();
    ListenableFuture<TestResponse> responseFuture = GrpcTestServiceGrpc
        .newFutureStub(channel).testCallService(request);
    Throwable thrown = catchThrowable(responseFuture::get);
    assertThat(thrown.getCause()).isInstanceOf(StatusRuntimeException.class);
    assertThat(((StatusRuntimeException) thrown.getCause()).getStatus().getCode())
        .isEqualTo(Code.INVALID_ARGUMENT);
    assertThat(thrown.getCause().getMessage())
        .contains("StringRequest must not be blank");
  }

  @Test
  void test_testCallService_givenServerInternalException_returnServerInternalException() throws InterruptedException {
    TestRequest request = TestRequest.newBuilder().setLongRequest(10).setStringRequest("ServerInternalException").build();
    ListenableFuture<TestResponse> responseFuture = GrpcTestServiceGrpc
        .newFutureStub(channel).testCallService(request);
    Throwable thrown = catchThrowable(responseFuture::get);
    assertThat(thrown.getCause()).isInstanceOf(StatusRuntimeException.class);
    assertThat(((StatusRuntimeException) thrown.getCause()).getStatus().getCode())
        .isEqualTo(Code.INTERNAL);
    assertThat(thrown.getCause().getMessage())
        .contains("Throw ServerInternalException");
  }

  @Test
  void test_testCallService_givenDataNotFoundException_returnDataNotFoundException() throws InterruptedException {
    TestRequest request = TestRequest.newBuilder().setLongRequest(10).setStringRequest("DataNotFoundException").build();
    ListenableFuture<TestResponse> responseFuture = GrpcTestServiceGrpc
        .newFutureStub(channel).testCallService(request);
    Throwable thrown = catchThrowable(responseFuture::get);
    assertThat(thrown.getCause()).isInstanceOf(StatusRuntimeException.class);
    assertThat(((StatusRuntimeException) thrown.getCause()).getStatus().getCode())
        .isEqualTo(Code.NOT_FOUND);
    assertThat(thrown.getCause().getMessage())
        .contains("Throw DataNotFoundException");
  }

  @Test
  void test_testCallService_givenNullPointerException_returnNullPointerException() throws InterruptedException {
    TestRequest request = TestRequest.newBuilder().setLongRequest(10).setStringRequest("NullPointerException").build();
    ListenableFuture<TestResponse> responseFuture = GrpcTestServiceGrpc
        .newFutureStub(channel).testCallService(request);
    Throwable thrown = catchThrowable(responseFuture::get);
    assertThat(thrown.getCause()).isInstanceOf(StatusRuntimeException.class);
    assertThat(((StatusRuntimeException) thrown.getCause()).getStatus().getCode())
        .isEqualTo(Code.INTERNAL);
    assertThat(thrown.getCause().getMessage())
        .contains("Throw NullPointerException");
  }

  @Test
  void test_testCallService_givenIllegalArgumentException_returnIllegalArgumentException() throws InterruptedException {
    TestRequest request = TestRequest.newBuilder().setLongRequest(10).setStringRequest("IllegalArgumentException").build();
    ListenableFuture<TestResponse> responseFuture = GrpcTestServiceGrpc
        .newFutureStub(channel).testCallService(request);
    Throwable thrown = catchThrowable(responseFuture::get);
    assertThat(thrown.getCause()).isInstanceOf(StatusRuntimeException.class);
    assertThat(((StatusRuntimeException) thrown.getCause()).getStatus().getCode())
        .isEqualTo(Code.INVALID_ARGUMENT);
    assertThat(thrown.getCause().getMessage())
        .contains("Throw IllegalArgumentException");
  }

  @Test
  void test_testCallServiceNoAnnotation_givenNegativeLongRequest_returnNegativeResponse() throws InterruptedException, ExecutionException {
    TestRequest request = TestRequest.newBuilder().setLongRequest(-1L).setStringRequest("test").build();
    ListenableFuture<TestResponse> responseFuture = GrpcTestServiceGrpc
        .newFutureStub(channel).testCallServiceNoAnnotation(request);
    TestResponse response = responseFuture.get();
    //Because there is no @GrpcRequestValidation, validation is not be called
    assertThat(response.getLongResponse()).isEqualTo(-1L);
    assertThat(response.getStringResponse()).isEqualTo("test");
  }

  @Test
  void test_callNoneGrpcServiceForLog_returnUnsupportedOperationException() {
    assertThatThrownBy(() -> grpcTestService.testCallNoneGrpcServiceForLog()).isInstanceOf(UnsupportedOperationException.class)
        .hasMessageContaining("GrpcServiceLog annotation can be only used in gRPC method. Please check testCallNoneGrpcServiceForLog");
  }

  @Test
  void test_callNoneGrpcServiceForValidation_returnUnsupportedOperationException() {
    assertThatThrownBy(() -> grpcTestService.testCallNoneGrpcServiceForValidation()).isInstanceOf(UnsupportedOperationException.class)
        .hasMessageContaining("GrpcRequestValidation annotation can be only used in gRPC method. Please check testCallNoneGrpcServiceForValidation");
  }
}
