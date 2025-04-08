package tqs.backend.cache;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CacheEntry {
    private double minTemperature;
    private double maxTemperature;
    private double precipitationProbability;
    private LocalDateTime timestamp;
    private LocalDate forecastDate;

    public CacheEntry(double minTemperature, double maxTemperature, double precipitationProbability, LocalDateTime timestamp, LocalDate forecastDate) {
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.precipitationProbability = precipitationProbability;
        this.timestamp = timestamp;
        this.forecastDate = forecastDate;
    }

    public double getMinTemperature() {
        return minTemperature;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public double getPrecipitationProbability() {
        return precipitationProbability;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public LocalDate getForecastDate() {
        return forecastDate;
    }

    public boolean isExpired(long ttlMinutes) {
        return LocalDateTime.now().isAfter(timestamp.plusMinutes(ttlMinutes));
    }
}
