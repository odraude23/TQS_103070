import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';  
import RestaurantConsumer from '../api_consumer/RestaurantConsumer';

import 'bootstrap/dist/css/bootstrap.min.css';
import Navbar from '../components/Navbar';

const HomePage = () => {
  const [restaurants, setRestaurants] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchRestaurants = async () => {
      try {
        const campusRestaurants = await RestaurantConsumer.getAllRestaurants();
        setRestaurants(campusRestaurants);
      } catch (error) {
        console.error("Error fetching restaurants:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchRestaurants();
  }, []);

  return (
    <div>
      <Navbar />
      <div className="text-center py-5" style={{ backgroundImage: 'url(/ua.jpg)', backgroundSize: 'cover', backgroundPosition: 'center', height: '400px' }}></div>
      <div className="container py-5">
        {loading ? (
          <div className="text-center">
            <div className="spinner-border text-success" role="status" />
            <p className="mt-2">Loading restaurants...</p>
          </div>
        ) : (
          <div className="row">
            {restaurants.map((restaurant) => (
              <div className="col-md-6 col-lg-4 mb-4 text-center" key={restaurant.id}>
                <div className="card shadow-lg border-0 rounded-3 overflow-hidden h-100">
                  <div className="card-body d-flex flex-column">
                    <h5 className="card-title text-success">{restaurant.name}</h5>
                    <p className="card-text text-muted">{restaurant.location}</p>
                    <div className="mt-auto">
                      <Link to={`/meal/${restaurant.id}`} className="btn btn-success w-100 mt-3">
                        View Menus
                      </Link>
                    </div>
                  </div>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
};

export default HomePage;
