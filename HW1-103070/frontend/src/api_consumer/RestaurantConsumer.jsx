const baseUrl = "http://localhost:8080/api/v1/restaurants";

const RestaurantConsumer = {
    getAllRestaurants: async () => {
        const response = await fetch(`${baseUrl}`);
        return await response.json();
    },

    getRestaurantById: async (id) => {
        const response = await fetch(`${baseUrl}/${id}`);
        return await response.json();
    }
};

export default RestaurantConsumer;