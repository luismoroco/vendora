package com.vendora.engine.kafka.model;

import lombok.Getter;

public enum KafkaTopic {
  ORDERS(1, (short) 1),
  MESSAGES(1, (short) 1);

  @Getter
  final int numPartitions;
  @Getter
  final short replicationFactor;

  KafkaTopic(int numPartitions, short replicationFactor) {
    this.numPartitions = numPartitions;
    this.replicationFactor = replicationFactor;
  }
}
