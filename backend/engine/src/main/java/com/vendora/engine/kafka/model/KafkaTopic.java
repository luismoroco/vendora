package com.vendora.engine.kafka.model;

import lombok.Getter;

@Getter
public enum KafkaTopic {
  ORDERS(1, (short) 1),
  MESSAGES(1, (short) 1),
  SHOPPING_CART(1, (short) 1),
  PRODUCT(1, (short) 1),
  NOTIFICATION(1, (short) 1);

  final int numPartitions;
  final short replicationFactor;

  KafkaTopic(int numPartitions, short replicationFactor) {
    this.numPartitions = numPartitions;
    this.replicationFactor = replicationFactor;
  }
}
