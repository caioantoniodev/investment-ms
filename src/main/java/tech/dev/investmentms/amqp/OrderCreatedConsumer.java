package tech.dev.investmentms.amqp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import tech.dev.investmentms.amqp.model.OrderCreatedEvent;

import static tech.dev.investmentms.config.RabbitConfig.ORDER_CREATED_QUEUE;

@Component
@Slf4j
public class OrderCreatedConsumer {

/*
    {"orderId": "b1223259-f495-4ece-8c97-2561c5cd92fe", "customerId": "0cfa8e24-bee1-4ff6-96b1-e11e71c8db1f", "items": [ {"ticket": "ETHE11", "quantity": 2} ]}
*/

    @RabbitListener(queues = ORDER_CREATED_QUEUE)
    public void listen(Message<OrderCreatedEvent> message) {
        log.info("Message consumed: {}", message);

        var payload = message.getPayload();
    }
}
