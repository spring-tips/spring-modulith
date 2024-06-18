package com.example.service.orders;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.modulith.ApplicationModuleInitializer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;

@RequestMapping("/orders")
@Controller
@ResponseBody
class OrderController {

    private final OrderService orderService;

    OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    void placeOrder(@RequestBody Order order) {
        this.orderService.placeOrder(order);
    }
}

@Service
@Transactional
class OrderService {

    private final ApplicationEventPublisher publisher;

    private final OrderRepository repository;

    OrderService(ApplicationEventPublisher publisher, OrderRepository repository) {
        this.publisher = publisher;
        this.repository = repository;
    }

    void placeOrder(Order order) {
        var saved = this.repository.save(order);
        System.out.println("saved [" + saved + "]");
        saved.lineItems()
                .stream()
                .map(li -> new OrderPlacedEvent(li.id(), li.quantity(), li.product()))
                .forEach(this.publisher::publishEvent);
    }

}

@Component
class ModuleInitializer implements ApplicationModuleInitializer {

    @Override
    public void initialize() {
        System.out.println("initializing module");
    }
}

interface OrderRepository extends ListCrudRepository<Order, Integer> {
}

@Table("orders_line_items")
record LineItem(@Id Integer id, int product, int quantity) {
}

@Table("orders")
record Order(@Id Integer id, Set<LineItem> lineItems) {
}
