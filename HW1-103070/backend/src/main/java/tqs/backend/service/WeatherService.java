package tqs.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tqs.backend.cache.CacheEntry;
import tqs.backend.cache.WeatherCache;
import org.json.JSONArray;
import org.json.JSONObject;

@Service
public class WeatherService {
    private static final Logger log = LoggerFactory.getLogger(WeatherService.class);
    private final WeatherCache cache = new WeatherCache(5);

    @Autowired
    private RequestAPIService requestAPIService;

    public CacheEntry getForecast(LocalDate date, int cityId) {   
        log.info("Fetching weather forecast for date: {} and city ID: {}", date, cityId);
        CacheEntry entry = cache.get(date);

        if (entry != null) {
            log.info("Cache hit for date: {} and city ID: {}", date, cityId);
            return entry;
        } 

        log.info("Cache miss for date: {} and city ID: {}", date, cityId);
        String response = requestAPIService.getForecastAPICall(cityId);

        if (response != null) {
            try {
                JSONObject jsonResponse = new JSONObject(response);
                JSONArray dataArray = jsonResponse.getJSONArray("data");
                
                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject dayData = dataArray.getJSONObject(i);
                    LocalDate forecastDate = LocalDate.parse(dayData.getString("forecastDate"));
                    
                    if (forecastDate.equals(date)) {
                        double minTemp = dayData.getDouble("tMin");
                        double maxTemp = dayData.getDouble("tMax");
                        double precipitation = dayData.getDouble("precipitaProb");
                        LocalDateTime timestamp = LocalDateTime.now();

                        entry = new CacheEntry(minTemp, maxTemp, precipitation, timestamp, forecastDate);
                        cache.put(date, entry);
                        return entry;
                    }
                }
            } catch (Exception e) {
                log.error("Error parsing weather forecast: {}", e.getMessage());
            }
        }

        return null; 
    }

    public String cacheStatistics() {
        log.info("Cache statistics: {}", cache.getStatistics());
        return cache.getStatistics();
    }
}
