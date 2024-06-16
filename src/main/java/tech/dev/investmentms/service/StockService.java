package tech.dev.investmentms.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockService {

    private final BrapiClient brapiClient;

    @Value("${brapi-api.token}")
    private String TOKEN;

    public Optional<BrapiClient.StockDto> getStock(String ticker) {
        var header = this.buildHeader();

        try {
            BrapiClient.BrapiResponseDto stockResponse = brapiClient.getStock(header, ticker);

            return stockResponse
                    .results()
                    .stream()
                    .findFirst();
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return Optional.empty();
    }

    private HttpHeaders buildHeader() {
        var headers = new HttpHeaders();
        headers.setBearerAuth(TOKEN);
        return headers;
    }
}
