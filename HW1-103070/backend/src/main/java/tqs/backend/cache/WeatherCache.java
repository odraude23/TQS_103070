package tqs.backend.cache;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WeatherCache {
    private final long ttlMinutes; // Time to live in minutes
    private final Map<LocalDate, CacheEntry> cache;
    private int hits = 0;
    private int misses = 0;
    private int puts = 0;

    public WeatherCache(long ttlMinutes) {
        this.ttlMinutes = ttlMinutes;
        this.cache = new ConcurrentHashMap<>();
    }

    public CacheEntry get(LocalDate date) {
        CacheEntry entry = cache.get(date);

        if (entry != null && !entry.isExpired(ttlMinutes)) {
            hits++;
            return entry;
        } 

        misses++;
        clearExpiredEntries();
        return null;
    }

    public boolean containsKey(LocalDate date) {
        return cache.containsKey(date);
    }

    public void put(LocalDate date, CacheEntry entry) {
        cache.put(date, entry);
        puts++;
    }

    public int getHits() {
        return hits;
    }

    public int getMisses() {
        return misses;
    }

    public int getPuts() {
        return puts;
    }

    public void clearAll() {
        cache.clear();
    }

    public List<LocalDate> getAllKeys() {
        return List.copyOf(cache.keySet());
    }

    public String getStatistics() {
        return String.format("Hits: %d, Misses: %d, Puts: %d", hits, misses, puts);
    }

    private void clearExpiredEntries() {
        cache.entrySet().removeIf(entry -> entry.getValue().isExpired(ttlMinutes));
    }
}
