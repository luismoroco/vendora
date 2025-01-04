package com.vendora.engine.kafka;

import com.vendora.engine.kafka.model.KafkaTopic;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class KafkaConfig {
  @Bean
  public List<NewTopic> setupTopics() {
    return Arrays.stream(KafkaTopic.values())
      .map(topic -> new NewTopic(topic.name(), topic.getNumPartitions(), topic.getReplicationFactor()))
      .toList();
  }
}
