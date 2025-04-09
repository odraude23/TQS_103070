import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import MealConsumer from '../api_consumer/MealConsumer';
import WeatherConsumer from '../api_consumer/WeatherConsumer';
import RestaurantConsumer from '../api_consumer/RestaurantConsumer';
import ReservationConsumer from '../api_consumer/ReservationConsumer'; // Import the Reservation API
import { FaTemperatureLow, FaTemperatureHigh, FaCloudRain, FaBoxOpen } from 'react-icons/fa';
import Navbar from '../components/Navbar';

const MealPage = () => {
  const { restaurantId } = useParams();
  const [groupedData, setGroupedData] = useState({});
  const [restaurant, setRestaurant] = useState(null);
  const [mealReservations, setMealReservations] = useState({}); // Track number of people per meal

  useEffect(() => {
    const fetchData = async () => {
      if (!restaurantId) return;

      const [meals, restaurantData] = await Promise.all([
        MealConsumer.getMealByRestaurantId(restaurantId),
        RestaurantConsumer.getRestaurantById(restaurantId)
      ]);

      setRestaurant(restaurantData);

      const weather = await WeatherConsumer.getAllWeather(restaurantData.cityId);

      const mealMap = meals.reduce((acc, meal) => {
        const date = meal.date;
        if (!acc[date]) acc[date] = {};
        acc[date][meal.mealType] = meal;
        return acc;
      }, {});

      const weatherMap = weather.reduce((acc, w) => {
        acc[w.forecastDate] = w;
        return acc;
      }, {});

      const combined = {};
      for (const date in mealMap) {
        combined[date] = {
          weather: weatherMap[date],
          lunch: mealMap[date]["lunch"],
          dinner: mealMap[date]["dinner"]
        };
      }

      setGroupedData(combined);
    };

    fetchData();
  }, [restaurantId]);

  // Handle reservation on button click
  const handleReservation = async (mealId, mealType) => {
    try {
      const numberOfPeople = mealReservations[mealId] || 1; // Default to 1 if no value is set
      const response = await ReservationConsumer.makeReservation(mealId, numberOfPeople);
      
      if (!response || !response.token) {
        alert('It is not possible to make a reservation for that number of people.');
        setMealReservations(prevState => ({ ...prevState, [mealId]: 1 })); // Reset input to 1 after failure
      } else {
        alert(`Reservation successful!\nToken: ${response.token}\nMeal ID: ${mealId}\nNumber of People: ${numberOfPeople}`);
        setMealReservations(prevState => ({ ...prevState, [mealId]: 1 })); // Reset input field after successful reservation
      }
    } catch (error) {
      alert('An error occurred while making the reservation.');
      setMealReservations(prevState => ({ ...prevState, [mealId]: 1 })); // Reset input to 1 after failure
    }
  };

  // Handle number of people input change for a specific meal
  const handleInputChange = (e, mealId) => {
    const value = e.target.value; // Allow any number, no max restriction
    setMealReservations(prevState => ({ ...prevState, [mealId]: value }));
  };

  return (
    <>
      <Navbar />
      <div className="container py-4">
        {restaurant && (
          <div className="mb-5 text-center">
            <h1 className="display-5">{restaurant.name}</h1>
          </div>
        )}

        {Object.entries(groupedData).map(([date, { weather, lunch, dinner }]) => {
          const formattedDate = new Date(date).toLocaleDateString(undefined, {
            weekday: 'long',
            year: 'numeric',
            month: 'long',
            day: 'numeric',
          });

          return (
            <div key={date} className="mb-5 text-center">
              <h2 className="h4 mb-4 border-bottom pb-2">{formattedDate}</h2>

              {weather ? (
                <div className="card mb-4 mx-auto shadow-sm border-info" style={{ maxWidth: '600px' }}>
                  <div className="card-body text-secondary d-flex justify-content-around flex-wrap gap-3">
                    <span>
                      <FaTemperatureLow className="me-2 text-primary" />
                      Min Temp: {weather.minTemperature}°C
                    </span>
                    <span>
                      <FaTemperatureHigh className="me-2 text-danger" />
                      Max Temp: {weather.maxTemperature}°C
                    </span>
                    <span>
                      <FaCloudRain className="me-2 text-info" />
                      Rain: {(
                        weather.precipitationProbability > 1
                          ? weather.precipitationProbability
                          : weather.precipitationProbability * 100
                      ).toFixed(0)}%
                    </span>
                  </div>
                </div>
              ) : (
                <p className="text-muted">No weather data available</p>
              )}

              <div className="row g-4 justify-content-center">
                {lunch && (
                  <div className="col-md-6">
                    <div className="card h-100 shadow-sm border-success">
                      <div className="card-header bg-success text-white">
                        🥗 Lunch: {lunch.name}
                      </div>
                      <div className="card-body text-start">
                        <p className="card-text">🍲 <strong>Soup:</strong> {lunch.soup}</p>
                        <p className="card-text">🍝 <strong>Main Course:</strong> {lunch.mainCourse}</p>
                        <p className="card-text">🍰 <strong>Dessert:</strong> {lunch.dessert}</p>
                        <p className="card-text text-muted">
                          <FaBoxOpen className="me-2" />
                          Reservation Limit: {lunch.reservationLimit}
                        </p>
                        {/* Input and Button for Reservation */}
                        <div className="mb-3">
                          <input
                            type="number"
                            className="form-control"
                            value={mealReservations[lunch.id] || 1}
                            onChange={(e) => handleInputChange(e, lunch.id)} // Update value for specific meal
                            min="1"
                          />
                        </div>
                        <button
                          className="btn btn-success w-100"
                          onClick={() => handleReservation(lunch.id, 'lunch')} // Pass meal ID to the reservation function
                        >
                          Reserve Lunch
                        </button>
                      </div>
                    </div>
                  </div>
                )}
                {dinner && (
                  <div className="col-md-6">
                    <div className="card h-100 shadow-sm border-primary">
                      <div className="card-header bg-primary text-white">
                        🍽️ Dinner: {dinner.name}
                      </div>
                      <div className="card-body text-start">
                        <p className="card-text">🍲 <strong>Soup:</strong> {dinner.soup}</p>
                        <p className="card-text">🍝 <strong>Main Course:</strong> {dinner.mainCourse}</p>
                        <p className="card-text">🍰 <strong>Dessert:</strong> {dinner.dessert}</p>
                        <p className="card-text text-muted">
                          <FaBoxOpen className="me-2" />
                          Reservation Limit: {dinner.reservationLimit}
                        </p>
                        {/* Input and Button for Reservation */}
                        <div className="mb-3">
                          <input
                            type="number"
                            className="form-control"
                            value={mealReservations[dinner.id] || 1}
                            onChange={(e) => handleInputChange(e, dinner.id)} // Update value for specific meal
                            min="1"
                          />
                        </div>
                        <button
                          className="btn btn-primary w-100"
                          onClick={() => handleReservation(dinner.id, 'dinner')} // Pass meal ID to the reservation function
                        >
                          Reserve Dinner
                        </button>
                      </div>
                    </div>
                  </div>
                )}
              </div>
            </div>
          );
        })}
      </div>
    </>
  );
};

export default MealPage;
