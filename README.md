# Overview

Spring-boot library that will auto-inject support for gRPC functionality.

* Entry/Exit log of gRPC service [`DONE`]
* gRPC exception handler [`DONE`]
* gRPC request validation [`DONE`]


# Usage

Any spring-boot application can make use of this library to support gRPC common functionalities:

* **GrpcRequestValidation** - Validation of gRPC request
* **GrpcServiceLog** - Log of gRPC service API entry and exit
* **ExceptionHandler** - Map Java RuntimeException to StatusRuntimeException

1. Include the dependency:

```
    <dependency>
      <groupId>com.arthur</groupId>
      <artifactId>arthur-common-grpc</artifactId>
      <version>${arthur-grpc.version}</version>
    </dependency>
```

2. gRPC service API entry/exit log:

Applications can only use annotation **@GrpcServiceLog** on Spring managed beans method It gives log before and after gRPC API service.<br>

```java
import com.arthur.grpc.annotations.GrpcRequestValidation;
import com.arthur.grpc.annotations.GrpcServiceLog;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.server.service.GrpcService;

@Log4j2
@GrpcService
public class GrpcTestService extends GrpcTestServiceGrpc.GrpcTestServiceImplBase {

  @Override
  @GrpcServiceLog
  public void testCallService(TestRequest request,
      io.grpc.stub.StreamObserver<TestResponse> responseObserver) {
    //...
  }
}

```

3. **gRPC request validation**

Applications can only use annotation **@GrpcRequestValidation** on Spring managed beans method

```java
import com.arthur.grpc.annotations.GrpcRequestValidation;
import com.arthur.grpc.annotations.GrpcServiceLog;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.server.service.GrpcService;

@Log4j2
@GrpcService
public class GrpcTestService extends GrpcTestServiceGrpc.GrpcTestServiceImplBase {

  @Override
  @GrpcRequestValidation
  public void testCallService(TestRequest request,
      io.grpc.stub.StreamObserver<TestResponse> responseObserver) {
    //...
  }
}
```

Then create a new Java class which extends abstract class `GrpcServiceValidationAspect`, in abstract method `validateGrpcRequest`, add whatever validations
needed for gRPC request

```java
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
        throw new IllegalArgumentException("LongRequest must be positive");
      }
      if (StringUtils.isBlank(request.getStringRequest())) {
        throw new IllegalArgumentException("StringRequest must not be blank");
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
    }
  }
}
```

4. **Java Runtime Exceptions to gRPC status runtime exception**

Java Runtime Exceptions is caught by ErrorHandler and return gRPC StatusRuntimeException as response to gRPC client

- ServerInternalException
- IllegalArgumentException
- DataNotFoundException
- RuntimeException

## Exception Mappings

The table below shows how the Java Runtime Exceptions are mapped to gRPC StatusRuntimeException:

## AWS

|   Java Runtime Exception    |  gRPC Status Code |             Description of Metric             |
|-----------------------------|-------------------|-----------------------------------------------|
| **ServerInternalException** |      INTERNAL     |    Internal Server exception                  |
| **IllegalArgumentException**|  INVALID_ARGUMENT |    gRPC request argument invalid exception    |
| **DataNotFoundException**   |     NOT_FOUND     |    Data not found exception                   |
| **RuntimeException**        |      INTERNAL     |    Internal Server exception                  |
|

# References

* [gRPC](https://grpc.io/)
* [gRPC Java](https://github.com/grpc/grpc-java)
* [SpringBoot gRPC](https://yidongnan.github.io/grpc-spring-boot-starter/en/)