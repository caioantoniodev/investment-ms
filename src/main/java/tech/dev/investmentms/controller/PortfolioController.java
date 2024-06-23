package tech.dev.investmentms.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.dev.investmentms.service.PortfolioService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/portfolio")
public class PortfolioController {

    private final PortfolioService portfolioService;

    @GetMapping("/customers/{id}")
    public ResponseEntity<PortfolioDto> portfolio(@PathVariable String id) {
        return ResponseEntity.ok(portfolioService.getPortfolio(id));
    }

    public record PortfolioDto(String customerId, Double netWorth) {}

}
