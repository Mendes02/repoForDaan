import React from 'react';
import { Link } from 'react-router-dom';
import '../Navbar/Navbar.css';
import { jwtDecode } from 'jwt-decode';

const Navbar = () => {
  // Function to get userId from the accessToken
  const getUserId = () => {
    const token = sessionStorage.getItem('accessToken');
    if (token) {
      const decodedToken = jwtDecode(token);
      return decodedToken.userId;
    }
    return null;
  };

  const userId = getUserId();

  return (
    <nav>
      <ul className="navbar">
        <li><Link to="/home-page">Home Page</Link></li>
        <li><Link to="/videogames">Video Games</Link></li>
        <li><Link to="/forums">Forums</Link></li>
        <li><Link to="/chats">Messages</Link></li>
        {userId && <li><Link to={`/userProfile/${userId}`}>Profile</Link></li>}
        <li><Link to="/logout">Logout</Link></li>
      </ul>
    </nav>
  );
};

export default Navbar;