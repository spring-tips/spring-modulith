package com.example.service.orders;

import org.springframework.modulith.events.Externalized;

@Externalized (target = "orders")
public record OrderPlacedEvent(
        int order,
        int quantity ,
        int product
) {
}
