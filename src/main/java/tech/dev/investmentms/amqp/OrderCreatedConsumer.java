package tech.dev.investmentms.amqp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import tech.dev.investmentms.service.PortfolioService;

import java.util.List;

import static tech.dev.investmentms.config.RabbitConfig.ORDER_CREATED_QUEUE;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderCreatedConsumer {

    private final PortfolioService portfolioService;

    @RabbitListener(queues = ORDER_CREATED_QUEUE)
    public void listen(Message<OrderCreatedConsumer.OrderCreatedEvent> message) {
        log.info("Message consumed: {}", message);

        var payload = message.getPayload();

        portfolioService.savePortfolio(payload);
    }

    public record OrderCreatedEvent(String orderId,
                             String customerId,
                             List<OrderItemEvent> items) {
    }

    public record OrderItemEvent(String ticker, Integer quantity) {
    }
}


