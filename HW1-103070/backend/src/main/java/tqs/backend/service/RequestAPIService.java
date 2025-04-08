package tqs.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RequestAPIService {
    private static final Logger log = LoggerFactory.getLogger(RequestAPIService.class);
    private static final String API_URL = "https://api.ipma.pt/open-data/forecast/meteorology/cities/daily/";
    
    public String getForecastAPICall(int cityId) {
        log.info("Fetching weather forecast for city ID: {}", cityId);
        String url = API_URL + cityId + ".json";
        
        try {
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(url, String.class);
            log.info("Weather forecast response: {}", response);

            return response;
        } 
        catch (Exception e) {
            log.error("Error fetching weather forecast: {}", e.getMessage());
            return null;
        }
    }
}
