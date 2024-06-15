package tech.dev.investmentms.amqp.model;

import java.util.List;

public record OrderCreatedEvent(String orderId,
                                String customerId,
                                List<OrderItemEvent> items) {
}