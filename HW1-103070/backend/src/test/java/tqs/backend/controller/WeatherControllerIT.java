package tqs.backend.controller;

import tqs.backend.cache.CacheEntry;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WeatherControllerIT {

    @LocalServerPort
    private int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    private final int cityId = 1010500;  
    private final LocalDate targetDate = LocalDate.of(2025, 4, 8);  // Example date, ensure this is valid for testing

    @Test
    void whenValidDateAndCity_thenReturnsForecast() throws Exception {
        String url = "http://localhost:" + randomServerPort + "/api/v1/weather/" + cityId;

        HttpEntity<LocalDate> requestEntity = new HttpEntity<>(targetDate);

        ResponseEntity<CacheEntry> response = restTemplate.exchange(
                url, HttpMethod.POST, requestEntity, CacheEntry.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMinTemperature()).isGreaterThan(0);
        assertThat(response.getBody().getMaxTemperature()).isGreaterThan(0);
        assertThat(response.getBody().getPrecipitationProbability()).isBetween(0.0, 100.0);
    }

    @Test
    void whenInvalidDate_thenReturnsEmpty() throws Exception {
        LocalDate invalidDate = LocalDate.of(2025, 12, 25);  

        String url = "http://localhost:" + randomServerPort + "/api/v1/weather/" + cityId;

        HttpEntity<LocalDate> requestEntity = new HttpEntity<>(invalidDate);

        ResponseEntity<String> response = restTemplate.exchange(
                url, HttpMethod.POST, requestEntity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNull();
    }

    @Test
    void whenStatisticsRequested_thenReturnsCacheStats() throws Exception {
        String url = "http://localhost:" + randomServerPort + "/api/v1/weather/statistics";

        ResponseEntity<String> response = restTemplate.exchange(
                url, HttpMethod.GET, null, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("Hits:");
        assertThat(response.getBody()).contains("Misses:");
        assertThat(response.getBody()).contains("Puts:");
    }
}
