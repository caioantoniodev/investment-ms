package tech.dev.investmentms.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Builder
@Data
public class PortfolioAsset {

    private String currency;
    private String shortName;
    private String longName;
    private String symbol;
    @Field(targetType = FieldType.DOUBLE)
    private Double regularMarketPrice;
    @Field(targetType = FieldType.INT64)
    private Integer quantity;
    private String error;
}
