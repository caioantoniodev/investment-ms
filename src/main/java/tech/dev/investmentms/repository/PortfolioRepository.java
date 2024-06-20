package tech.dev.investmentms.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import tech.dev.investmentms.entity.Portfolio;

import java.util.Optional;

public interface PortfolioRepository extends MongoRepository<Portfolio, String> {
    Optional<Portfolio> findByCustomerId(String customerId);
}