package org.example.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "group-service")
public interface GroupServiceClient {
}
