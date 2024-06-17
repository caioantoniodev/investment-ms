package tech.dev.investmentms.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.math.BigDecimal;
import java.util.List;

@FeignClient(
        name = "BrapiClient",
        url = "${brapi-api.uri}"
)
public interface BrapiClient {

    @GetMapping(value = "/api/quote/{ticker}")
    BrapiResponseDto getStock(@RequestHeader HttpHeaders headers,
                              @PathVariable("ticker") String ticker);

    record BrapiResponseDto(List<StockDto> results) {
    }

    record StockDto(String currency,
                   String shortName,
                   String longName,
                   String symbol,
                   BigDecimal regularMarketPrice) {
    }
}