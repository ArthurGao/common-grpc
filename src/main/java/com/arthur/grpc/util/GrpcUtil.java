package com.arthur.grpc.util;

import com.google.protobuf.ByteString;
import com.google.protobuf.ProtocolStringList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GrpcUtil {

  private GrpcUtil() {
  }

  public static List<String> getListOfStringFromProtocolStringList(ProtocolStringList protocolStringList) {
    return getStringStreamFromProtoclStringList(protocolStringList)
        .collect(Collectors.toList());
  }


  public static Set<String> getSetOfStringFromProtocolStringList(ProtocolStringList protocolStringList) {
    return getStringStreamFromProtoclStringList(protocolStringList)
        .collect(Collectors.toSet());
  }

  private static Stream<String> getStringStreamFromProtoclStringList(ProtocolStringList protocolStringList) {
    return protocolStringList.asByteStringList()
        .stream()
        .map(ByteString::toStringUtf8);
  }

}
