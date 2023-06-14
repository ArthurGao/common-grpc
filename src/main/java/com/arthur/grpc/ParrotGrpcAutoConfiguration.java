package com.arthur.grpc;

import com.arthur.grpc.config.arthurGrpcConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({arthurGrpcConfiguration.class})
public class arthurGrpcAutoConfiguration {

}
