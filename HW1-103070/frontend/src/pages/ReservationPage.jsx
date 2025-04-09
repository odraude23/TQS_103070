import React, { useState } from "react";
import ReservationConsumer from "../api_consumer/ReservationConsumer";
import Navbar from "../components/Navbar";

const ReservationPage = () => {
  const [token, setToken] = useState(""); // State for input token
  const [message, setMessage] = useState(""); // State for displaying message
  const [reservationDetails, setReservationDetails] = useState(null); // State to store reservation details
  const [isLoading, setIsLoading] = useState(false); // Loading state
  const [isActionLoading, setIsActionLoading] = useState(false); // Loading state for action buttons

  const handleSubmit = async (e) => {
    e.preventDefault();
    setIsLoading(true); // Set loading state to true while checking
    setMessage(""); // Reset message before new API call
    setReservationDetails(null); // Clear previous reservation details

    try {
      // Get the reservation using the token
      const reservation = await ReservationConsumer.getReservation(token);

      if (reservation) {
        // Handle different reservation states
        console.log(reservation);
        console.log(reservation.used, reservation.cancelled);
        if (reservation.used) {
          setMessage("This reservation has already been used.");
        } else if (reservation.cancelled) {
          setMessage("This reservation has been cancelled.");
        } else {
          setMessage("This token is valid!");
          setReservationDetails(reservation); // Save the valid reservation details
        }
      } else {
        setMessage("Token is invalid.");
      }
    } catch (error) {
      setMessage("This token is invalid.");
    } finally {
      setIsLoading(false); // Set loading state to false after API call
    }
  };

  const handleMarkAsUsed = async () => {
    if (reservationDetails) {
      setIsActionLoading(true);
      try {
        await ReservationConsumer.markAsUsed(reservationDetails.token);
        setMessage("Reservation marked as used.");
        setReservationDetails(prevState => ({ ...prevState, used: true }));
      } catch (error) {
        setMessage("An error occurred while marking the reservation as used.");
      } finally {
        setIsActionLoading(false);
      }
    }
  };

  const handleCancelReservation = async () => {
    if (reservationDetails) {
      setIsActionLoading(true);
      try {
        await ReservationConsumer.cancelReservation(reservationDetails.token);
        setMessage("Reservation cancelled.");
        setReservationDetails(prevState => ({ ...prevState, cancelled: true }));
      } catch (error) {
        setMessage("An error occurred while cancelling the reservation.");
      } finally {
        setIsActionLoading(false);
      }
    }
  };

  return (
    <>
      <Navbar />
      <div className="container mt-5">
        <h2 className="text-center">Check Reservation Token</h2>
        <div className="row justify-content-center mt-4">
          <div className="col-md-6">
            <form onSubmit={handleSubmit} className="card p-4 shadow-sm">
              <div className="mb-3">
                <label htmlFor="tokenInput" className="form-label">
                  Enter Reservation Token
                </label>
                <input
                  type="text"
                  className="form-control"
                  id="tokenInput"
                  value={token}
                  onChange={(e) => setToken(e.target.value)}
                  placeholder="Enter your token here"
                  required
                />
              </div>
              <button type="submit" className="btn btn-primary w-100" disabled={isLoading}>
                {isLoading ? (
                  <span className="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>
                ) : (
                  "Check Token"
                )}
              </button>
            </form>
          </div>
        </div>

        {message && (
          <div className={`mt-4 text-center alert ${message.includes("invalid") ? "alert-danger" : "alert-success"}`} role="alert">
            {message}
          </div>
        )}

        {reservationDetails && !reservationDetails.used && !reservationDetails.cancelled && (
          <div className="mt-4 card p-4 shadow-sm">
            <h4>Reservation Details</h4>
            <ul className="list-group list-group-flush">
              <li className="list-group-item">
                <strong>Token:</strong> {reservationDetails.token}
              </li>
              <li className="list-group-item">
                <strong>Meal:</strong> {reservationDetails.meal.name}
              </li>
              <li className="list-group-item">
                <strong>Number of People:</strong> {reservationDetails.numberOfPeople}
              </li>
              <li className="list-group-item">
                <strong>Reservation Date and Time:</strong> {new Date(reservationDetails.reservationDateTime).toLocaleString()}
              </li>
            </ul>

            <div className="mt-3 d-flex justify-content-between">
              <button
                className="btn btn-success w-48"
                onClick={handleMarkAsUsed}
                disabled={isActionLoading}
              >
                {isActionLoading ? (
                  <span className="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>
                ) : (
                  "Mark as Used"
                )}
              </button>
              <button
                className="btn btn-danger w-48"
                onClick={handleCancelReservation}
                disabled={isActionLoading}
              >
                {isActionLoading ? (
                  <span className="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>
                ) : (
                  "Cancel Reservation"
                )}
              </button>
            </div>
          </div>
        )}
      </div>
    </>
  );
};

export default ReservationPage;
