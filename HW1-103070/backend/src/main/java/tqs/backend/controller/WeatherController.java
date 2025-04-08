package tqs.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tqs.backend.cache.CacheEntry;
import tqs.backend.service.WeatherService;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @PostMapping("/weather/{cityId}")
    public CacheEntry getWeatherForecast(@RequestBody LocalDate date, @PathVariable(value = "cityId") int cityId) {
        CacheEntry entry = weatherService.getForecast(date, cityId);
        return entry;
    }
    
    @GetMapping("/weather/statistics")
    public String getStatistics() {
        return weatherService.cacheStatistics();
    }
}
