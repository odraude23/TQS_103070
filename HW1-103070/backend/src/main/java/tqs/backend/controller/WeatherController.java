package tqs.backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tqs.backend.cache.CacheEntry;
import tqs.backend.service.WeatherService;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/weather/{cityId}")
    public CacheEntry getWeatherForecast(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @PathVariable("cityId") int cityId) {
        return weatherService.getForecast(date, cityId);
    }
    
    @GetMapping("/weather/statistics")
    public String getStatistics() {
        return weatherService.cacheStatistics();
    }

    @GetMapping("/weather/all/{cityId}")
    public List<CacheEntry> getAllWeatherForecast(@PathVariable("cityId") int cityId) {
        return weatherService.getAllForecasts(cityId);
    }
}
