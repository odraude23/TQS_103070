const baseUrl = "http://localhost:8080/api/v1/reservations";

const ReservationConsumer = {
    getReservation: async (token) => {
        const response = await fetch(`${baseUrl}/${token}`);
        return await response.json();
    },

    makeReservation: async (mealId, numberOfPeople) => {
        const response = await fetch(`${baseUrl}/${mealId}`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(numberOfPeople),
        });
        return await response.json();
    },

    markAsUsed: async (token) => {
        const response = await fetch(`${baseUrl}/used/${token}`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
        });
        return await response.json();
    },

    cancelReservation: async (token) => {
        const response = await fetch(`${baseUrl}/cancel/${token}`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
        });
        return await response.json();
    }
};

export default ReservationConsumer;