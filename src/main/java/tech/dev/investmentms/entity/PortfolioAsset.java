package tech.dev.investmentms.entity;

import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;

public class PortfolioAsset {

    private String currency;
    private String shortName;
    private String longName;
    private String symbol;
    private BigDecimal regularMarketPrice;
    @Field(targetType = FieldType.INT64)
    private Integer quantity;
}
