const baseUrl = "http://localhost:8080/api/v1/meals";

const MealConsumer = {
    getAllMeals: async () => {
        const response = await fetch(`${baseUrl}`);
        return await response.json();
    },

    getMealById: async (id) => {
        const response = await fetch(`${baseUrl}/${id}`);
        return await response.json();
    },

    getMealByRestaurantId: async (restaurantId) => {
        const response = await fetch(`${baseUrl}/restaurant/${restaurantId}`);
        return await response.json();
    }
};

export default MealConsumer;