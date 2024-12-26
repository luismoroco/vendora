package com.vendora.engine.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.time.LocalDateTime;

@Embeddable
public class Auditable {
  @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at", nullable = false, insertable = false)
  private LocalDateTime updatedAt;
}
