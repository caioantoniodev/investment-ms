package tech.dev.investmentms.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.dev.investmentms.amqp.model.OrderCreatedEvent;
import tech.dev.investmentms.entity.Portfolio;
import tech.dev.investmentms.entity.PortfolioAsset;
import tech.dev.investmentms.repository.PortfolioRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PortfolioService {
    private final PortfolioRepository portfolioRepository;

    private final StockService stockService;

    public void savePortfolio(OrderCreatedEvent orderCreatedEvent) {

        List<PortfolioAsset> list = orderCreatedEvent.items().stream()
                .map(orderItemEvent -> {
                    var optionalStock = stockService.getStock(orderItemEvent.ticker());

                    PortfolioAsset.PortfolioAssetBuilder assetBuilder = PortfolioAsset.builder();

                    if (optionalStock.isEmpty()) {
                        return assetBuilder
                                .symbol(orderItemEvent.ticker())
                                .error("Stock not found")
                                .build();
                    }

                    BrapiClient.StockDto stock = optionalStock.get();

                    return assetBuilder
                            .currency(stock.currency())
                            .longName(stock.longName())
                            .shortName(stock.shortName())
                            .quantity(orderItemEvent.quantity())
                            .symbol(stock.symbol())
                            .regularMarketPrice(stock.regularMarketPrice())
                            .build();
                }).toList();

        var portfolio = Portfolio.builder()
                .orderId(orderCreatedEvent.orderId())
                .customerId(orderCreatedEvent.customerId())
                .assets(list).build();

       var saved =  portfolioRepository.save(portfolio);

        log.info("Stock saved: {}", saved);
    }
}
