package com.arthur.grpc.config;

import com.arthur.grpc.aop.GrpcServiceLogAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "com.arthur.grpc.aop")
public class arthurGrpcConfiguration {

  @Bean
  public GrpcServiceLogAspect grpcServiceLogAspect() {
    return new GrpcServiceLogAspect();
  }

}
