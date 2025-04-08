package tqs.backend.cache;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WeatherCacheTest {
    private WeatherCache cache;
    private LocalDate testDate;
    private CacheEntry validEntry;

    @BeforeEach
    void setUp() {
        cache = new WeatherCache(10); // 10 minute TTL
        testDate = LocalDate.now();
        validEntry = new CacheEntry(12.5, 25.0, 0.1, LocalDateTime.now(), testDate);
    }

    @Test
    void testPutAndGet() {
        cache.put(testDate, validEntry);
        CacheEntry result = cache.get(testDate);
        assertNotNull(result);
        assertEquals(validEntry.getMinTemperature(), result.getMinTemperature());
        assertEquals(1, cache.getHits());
        assertEquals(0, cache.getMisses());
        assertEquals(1, cache.getPuts());
    }

    @Test
    void testGetReturnsNullIfExpired() {
        CacheEntry expiredEntry = new CacheEntry(10, 20, 0.5,
                LocalDateTime.now().minusMinutes(15), testDate); // expired

        cache.put(testDate, expiredEntry);
        CacheEntry result = cache.get(testDate);
        assertNull(result);
        assertEquals(0, cache.getHits());
        assertEquals(1, cache.getMisses());
    }

    @Test
    void testContainsKey() {
        cache.put(testDate, validEntry);
        assertTrue(cache.containsKey(testDate));
    }

    @Test
    void testClearAll() {
        cache.put(testDate, validEntry);
        cache.clearAll();
        assertFalse(cache.containsKey(testDate));
    }

    @Test
    void testGetAllKeys() {
        LocalDate date1 = LocalDate.now();
        LocalDate date2 = date1.plusDays(1);

        cache.put(date1, validEntry);
        cache.put(date2, new CacheEntry(5, 15, 0.2, LocalDateTime.now(), date2));

        assertEquals(2, cache.getAllKeys().size());
        assertTrue(cache.getAllKeys().contains(date1));
        assertTrue(cache.getAllKeys().contains(date2));
    }

    @Test
    void testStatistics() {
        cache.put(testDate, validEntry);
        cache.get(testDate);
        cache.get(LocalDate.now().plusDays(1)); // Miss

        String stats = cache.getStatistics();
        assertTrue(stats.contains("Hits: 1"));
        assertTrue(stats.contains("Misses: 1"));
        assertTrue(stats.contains("Puts: 1"));
    }
}
