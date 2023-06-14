package com.arthur.test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@Log4j2
@SpringBootApplication
public class GrpcTestServiceApplication {

  private static final String GRPC_PORT = "grpc.server.port";

  public static void main(String[] args) throws UnknownHostException {
    Environment env = SpringApplication.run(GrpcTestServiceApplication.class, args).getEnvironment();
    String protocol = "gRPC";
    log.info("\n----------------------------------------------------------\n\t" +
            "Application '{}' is running! Access URLs:\n\t" +
            "Local: \t\t{}://localhost:{}\n\t" +
            "External: \t{}://{}:{}\n\t" +
            "gRPC port: \t{}\n" +
            "Profile(s): \t{}\n----------------------------------------------------------",
        env.getProperty("spring.application.name"),
        protocol,
        env.getProperty(GRPC_PORT),
        protocol,
        InetAddress.getLocalHost().getHostAddress(),
        env.getProperty(GRPC_PORT),
        env.getProperty(GRPC_PORT),
        env.getActiveProfiles());
  }
}
