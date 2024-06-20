package tech.dev.investmentms.repository;

import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Component
@RequiredArgsConstructor
public class CustomPortfolioRepository {

    private final MongoTemplate mongoTemplate;

    public Optional<Double> getNetWorth(String customerId) {
        var aggregation = newAggregation(
                match(Criteria.where("customerId").is(customerId)),
                unwind("$assets"),
                project().and("assets.regularMarketPrice").multiply("assets.quantity").as("priceAccumulator"),
                group().sum("priceAccumulator").as("netWorth")
        );

        var result = mongoTemplate.aggregate(aggregation, "portfolio", Document.class);

        return Optional.ofNullable(result.getUniqueMappedResult())
                .map(doc -> doc.getDouble("netWorth"));
    }


    /*db.portfolio.aggregate([
      {
        $match: {
          customerId: "3a8286f6454539ae2a00cfb3f42715fd5415c0b42dd90fa3f5964ac00aaf87d4"
        }
      },
      {
        $unwind: "$assets"
      },
      {
        $group: {
          _id: "$customerId",
          netWorth: {
            $sum: {
              $multiply: ["$assets.regularMarketPrice", "$assets.quantity"]
            }
          }
        }
      }
    ])*/
}
