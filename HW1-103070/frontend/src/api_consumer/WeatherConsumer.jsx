const baseUrl = "http://localhost:8080/api/v1/weather";

const WeatherConsumer = {
    getWeatherByCity: async (cityId, date) => {
        const response = await fetch(`${baseUrl}/${cityId}?date=${date}`);
        return await response.json();
    },

    getAllWeather: async (cityId) => {
        const response = await fetch(`${baseUrl}/all/${cityId}`);
        return await response.json();
    }
};

export default WeatherConsumer;