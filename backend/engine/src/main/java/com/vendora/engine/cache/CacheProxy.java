package com.vendora.engine.cache;

import com.vendora.engine.cache.model.CacheTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class CacheProxy {
  private final CacheManager cacheManager;

  private static final Logger LOGGER = LoggerFactory.getLogger(CacheProxy.class);

  public CacheProxy(
    CacheManager cacheManager
  ) {
    this.cacheManager = cacheManager;
  }

  public <V, K extends String> void put(CacheTopic topic, K key, V value) {
    var cache = this.cacheManager.getCache(topic.toString());
    cache.put(key, value);
  }

  public <V, K extends String> Optional<V> get(CacheTopic topic, K key, Class<V> valueType) {
    var cache = this.cacheManager.getCache(topic.toString());

    var v = cache.get(key, valueType);
    if (Objects.isNull(v)) {
      LOGGER.warn("Cache miss [topic=%s][key=%s]".formatted(topic.toString(), key));

      return Optional.empty();
    }

    return Optional.of(v);
  }

  public <K extends String> void evict(CacheTopic topic, K key) {
    var cache = this.cacheManager.getCache(topic.toString());

    cache.evict(key);
    LOGGER.info("Value removed [topic=%s][key=%s]".formatted(topic.toString(), key));
  }
}