package tech.dev.investmentms.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import tech.dev.investmentms.entity.Portfolio;

public interface PortfolioRepository extends MongoRepository<Portfolio, String> {
}