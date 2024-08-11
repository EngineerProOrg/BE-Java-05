package com.engineerpro.rest.example.service;

import org.springframework.stereotype.Service;

@Service
public interface DistributedLockService {
  boolean accquireLock(String idempotentKey);
  // e.g. idempotentKey = receipt_{receipt_id}
  void releaseLock(String idempotentKey);
}
