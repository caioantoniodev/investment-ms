package tech.dev.investmentms.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Builder
@Document
@Data
public class Portfolio {

    @MongoId
    private String portfolioId;

    @Indexed(name = "customer_id_idx")
    private String customerId;

    @Indexed(name = "order_id_idx")
    private String orderId;

    private List<PortfolioAsset> assets;
}
