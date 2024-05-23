import React, { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import getVideoGameById from '../APILayer/getVideoGame';
import "../Pages/static/App.css";
import Navbar from '../Components/Navbar/Navbar';

function VideoGame() {
  const [videoGame, setVideoGame] = useState(null);
  const { id } = useParams();

  useEffect(() => {
    const fetchVideoGame = async () => {
      const data = await getVideoGameById(id);
      if (data) {
        setVideoGame(data);
      }
    };

    fetchVideoGame();
  }, [id]);

  if (!videoGame) {
    return <div>Loading...</div>;
  }

  return (
    <div>
        <Navbar/>
        <div className="video-game-profile">
          <div className="video-game-header">
            <h1>{videoGame.name}</h1>
            <div className="video-game-info">
              <span>Publisher: {videoGame.publisher}</span>
              <span>Release Date: {videoGame.released}</span>
            </div>
          </div>
          <div className="video-game-actions">
            <Link to={`/forums/${id}`} className="btn btn-reviews">Forum</Link>
            <Link to={`/videogameReviews/${id}`} className="btn btn-reviews">Reviews</Link>
          </div>
        </div>
    </div>
  );
}

export default VideoGame;