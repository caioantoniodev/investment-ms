package tech.dev.investmentms.amqp.model;

public record OrderItemEvent(String ticket, Integer quantity) {
}
