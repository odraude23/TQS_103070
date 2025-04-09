import React from 'react';
import { Link } from 'react-router-dom'; 
import { FaUtensils } from 'react-icons/fa';

const Navbar = () => {
  return (
    <nav className="navbar navbar-expand-lg navbar-dark" style={{ backgroundColor: '#28a745' }}>
      <div className="container-fluid">

        <img
          src="/Marca-UA-Complementar-PRETO.png" 
          alt="Moliceiro Logo"
          className="navbar-brand"
          style={{ width: '180px', height: 'auto' }}
        />

        <div className="mx-auto d-flex justify-content-center">
          <h1 className="display-5 fw-bold text-white">
            <FaUtensils className="me-2" />
            Moliceiro University Campus Restaurants
          </h1>
        </div>

        <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
          <span className="navbar-toggler-icon"></span>
        </button>

        <div className="collapse navbar-collapse" id="navbarNav">
          <ul className="navbar-nav ms-auto">
            <li className="nav-item">
              <h4>
                <Link className="nav-link" to="/">
                  Restaurants
                </Link>
              </h4>
            </li>

            <li className="nav-item">
              <h4>
                <a className="nav-link" href="/reservation">
                  Reservations
                </a>
              </h4>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
