import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom'; // Import Link
import "../Pages/static/App.css";
import Navbar from '../Components/Navbar/Navbar';
import getTopForums from '../APILayer/getTopForums';

function ForumsPage() {
  const [forums, setForums] = useState([]);

  useEffect(() => {
    getTopForums().then(data => setForums(data)).catch(error => console.error(error));
  }, []);

  return (
    <div>
      <Navbar/>
      <h2 className='forumTitle'>Top Forums</h2>
      <div className="forum-list">
        {forums.length > 0 ? (
          forums.map(forum => (
            <div key={forum.id} className="forum-item">
              <Link to={`/forums/${forum.id}`}>
                <h3>{forum.videoGame.name}</h3>
              </Link>
              <p>Publisher: {forum.videoGame.publisher}</p>
              <p>Release Date: {forum.videoGame.released}</p>
            </div>
          ))
        ) : (
          <p>No forums available.</p>
        )}
      </div>
    </div>
  );
}

export default ForumsPage;

