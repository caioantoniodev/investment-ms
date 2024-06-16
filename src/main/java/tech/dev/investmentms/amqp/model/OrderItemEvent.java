package tech.dev.investmentms.amqp.model;

public record OrderItemEvent(String ticker, Integer quantity) {
}
