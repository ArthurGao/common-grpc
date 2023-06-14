package com.arthur.grpc.config;

import com.arthur.grpc.aop.GrpcServiceValidationAspect;
import com.arthur.grpc.aop.GrpcServiceValidationAspectImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class arthurTestGrpcConfiguration {

  @Bean
  public GrpcServiceValidationAspect grpcServiceValidationAspect() {
    return new GrpcServiceValidationAspectImpl();
  }

}
