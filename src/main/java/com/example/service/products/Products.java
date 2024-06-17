package com.example.service.products;

import com.example.service.orders.OrderPlacedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
class Products {

// todo
//    private final PrivateThing privateThing;
//
//    Products(PrivateThing privateThing) {
//        this.privateThing = privateThing;
//    }

    @EventListener
    void on(OrderPlacedEvent ope) throws Exception {
        Thread.sleep(5_000);
        System.out.println("order placed event [" + ope + "]");
        Thread.sleep(5_000);
    }
}
