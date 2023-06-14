package com.arthur.grpc.util;

import static com.arthur.grpc.util.GrpcUtil.getListOfStringFromProtocolStringList;
import static com.arthur.grpc.util.GrpcUtil.getSetOfStringFromProtocolStringList;
import static org.assertj.core.api.Assertions.assertThat;

import com.google.protobuf.LazyStringArrayList;
import com.google.protobuf.ProtocolStringList;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

class GrpcUtilTest {

  @Test
  void test_GetListOfString_FromProtocolList() {

    ProtocolStringList protocolStringList = new LazyStringArrayList();
    protocolStringList.add("A");
    protocolStringList.add("B");
    protocolStringList.add("C");

    assertThat(getListOfStringFromProtocolStringList(protocolStringList)).hasSize(3)
        .isEqualTo(List.of("A", "B", "C"));

  }

  @Test
  void test_GetSetOfString_FromProtocolList() {

    ProtocolStringList protocolStringList = new LazyStringArrayList();
    protocolStringList.add("A");
    protocolStringList.add("A");
    protocolStringList.add("B");

    assertThat(getSetOfStringFromProtocolStringList(protocolStringList)).hasSize(2)
        .isEqualTo(Set.of("A", "B"));

  }

}