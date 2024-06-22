package tech.dev.investmentms.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;
import tech.dev.investmentms.amqp.OrderCreatedConsumer;
import tech.dev.investmentms.controller.PortfolioController;
import tech.dev.investmentms.entity.Portfolio;
import tech.dev.investmentms.entity.PortfolioAsset;
import tech.dev.investmentms.repository.CustomPortfolioRepository;
import tech.dev.investmentms.repository.PortfolioRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;

    private final CustomPortfolioRepository customPortfolioRepository;

    private final StockService stockService;

    public void savePortfolio(OrderCreatedConsumer.OrderCreatedEvent orderCreatedEvent) {

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
                .orderId(DigestUtils.sha256Hex(orderCreatedEvent.orderId()))
                .customerId(DigestUtils.sha256Hex(orderCreatedEvent.customerId()))
                .assets(list).build();

       var saved =  portfolioRepository.save(portfolio);

        log.info("Stock saved: {}", saved);
    }

   public PortfolioController.PortfolioDto getPortfolio(String customerId) {
       var byCustomerId = portfolioRepository.findByCustomerId(customerId);

       if (byCustomerId.isEmpty()) {
           return null;
       }

       var portfolio = byCustomerId.get();

       return customPortfolioRepository.getNetWorth(portfolio.getCustomerId())
               .map(netWorth ->
                       new PortfolioController.PortfolioDto(portfolio.getCustomerId(), netWorth))
              .orElse(null);
   }
}
