package tqs.backend.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import tqs.backend.cache.CacheEntry;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class WeatherService_UnitTest {

    @Mock
    private RequestAPIService requestAPIService;

    @InjectMocks
    private WeatherService weatherService;

    @Test
    void testGetForecastCacheMiss() {
        String mockApiResponse = "{ \"data\": [ { \"forecastDate\": \"2025-04-07\", \"tMin\": 10.0, \"tMax\": 20.0, \"precipitaProb\": 50.0 } ] }";
        when(requestAPIService.getForecastAPICall(1010500)).thenReturn(mockApiResponse);

        LocalDate date = LocalDate.of(2025, 4, 7);
        CacheEntry entry = weatherService.getForecast(date, 1010500);

        assertNotNull(entry);
        assertEquals(10.0, entry.getMinTemperature());
        assertEquals(20.0, entry.getMaxTemperature());
        assertEquals(50.0, entry.getPrecipitationProbability());
        assertNotNull(entry.getTimestamp());
        assertEquals(date, entry.getForecastDate());

        verify(requestAPIService, times(1)).getForecastAPICall(1010500);
    }

    @Test
    void testGetForecastCacheHit() {
        String mockApiResponse = "{ \"data\": [ { \"forecastDate\": \"2025-04-07\", \"tMin\": 10.0, \"tMax\": 20.0, \"precipitaProb\": 50.0 } ] }";
        when(requestAPIService.getForecastAPICall(1010500)).thenReturn(mockApiResponse);

        LocalDate date = LocalDate.of(2025, 4, 7);
        weatherService.getForecast(date, 1010500);  
        weatherService.getForecast(date, 1010500);  

        verify(requestAPIService, times(1)).getForecastAPICall(1010500);
    }

    @Test
    void testGetForecastCacheMissParsingError() {
        when(requestAPIService.getForecastAPICall(1010500)).thenReturn("Invalid API response");

        LocalDate date = LocalDate.of(2025, 4, 7);
        CacheEntry entry = weatherService.getForecast(date, 1010500);

        assertNull(entry);  
        verify(requestAPIService, times(1)).getForecastAPICall(1010500);
    }

    @Test
    void testGetForecastCacheStatistics() {
        String mockApiResponse = "{ \"data\": [ { \"forecastDate\": \"2025-04-07\", \"tMin\": 10.0, \"tMax\": 20.0, \"precipitaProb\": 50.0 } ] }";
        when(requestAPIService.getForecastAPICall(1010500)).thenReturn(mockApiResponse);

        LocalDate date = LocalDate.of(2025, 4, 7);
        weatherService.getForecast(date, 1010500); 
        weatherService.getForecast(date, 1010500);  

        String cacheStats = weatherService.cacheStatistics();
        assertNotNull(cacheStats);
        assertTrue(cacheStats.contains("Hits"));
        assertTrue(cacheStats.contains("Misses"));
        assertTrue(cacheStats.contains("Puts"));
    }

    @Test
    void testGetForecastNoCache() {
        String mockApiResponse = "{ \"data\": [ { \"forecastDate\": \"2025-04-07\", \"tMin\": 10.0, \"tMax\": 20.0, \"precipitaProb\": 50.0 } ] }";
        when(requestAPIService.getForecastAPICall(1010500)).thenReturn(mockApiResponse);

        LocalDate date = LocalDate.of(2025, 4, 7);
        CacheEntry entry = weatherService.getForecast(date, 1010500);

        assertNotNull(entry);
        assertEquals(10.0, entry.getMinTemperature());
        assertEquals(20.0, entry.getMaxTemperature());
        assertEquals(50.0, entry.getPrecipitationProbability());
        assertNotNull(entry.getTimestamp());
        assertEquals(date, entry.getForecastDate());

        verify(requestAPIService, times(1)).getForecastAPICall(1010500);
    }
}
