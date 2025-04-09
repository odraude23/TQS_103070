import { BrowserRouter, Routes, Route } from 'react-router-dom'
import HomePage from './pages/HomePage' 
import MealPage from './pages/MealPage'
import ReservationPage from './pages/ReservationPage'

function App() {
  return (
    <BrowserRouter>
      <Routes>
          <Route path="/" Component={HomePage} />
          <Route path="/meal/:restaurantId" Component={MealPage} />
          <Route path="/reservation" Component={ReservationPage} />
      </Routes>
    </BrowserRouter>
  )
}

export default App
