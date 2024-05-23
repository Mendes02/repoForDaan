import React, { useState, useEffect } from 'react';
import "../Pages/static/App.css";
import Navbar from '../Components/Navbar/Navbar';
import { Link } from 'react-router-dom';
import getFilteredVideoGames from '../APILayer/getFilteredVideoGames';

function VideoGames() {
  const [videoGames, setVideoGames] = useState([]);
  const [searchQuery, setSearchQuery] = useState('');
  const [selectedGenre, setSelectedGenre] = useState('');

  useEffect(() => {
    fetchVideoGames();
  }, [searchQuery, selectedGenre]);

  const fetchVideoGames = async () => {
    const filteredGames = await getFilteredVideoGames(selectedGenre, searchQuery);
    setVideoGames(filteredGames);
  };

  const handleSearchChange = (event) => {
    setSearchQuery(event.target.value);
  };

  const handleGenreChange = (event) => {
    setSelectedGenre(event.target.value);
  };

  return (
    <div>
      <Navbar/>
      <div className='videoGamesBackground'>
        <h2 className='topVideoGamesTitle'>Video games</h2>

        <input 
          type="text" 
          placeholder="Search by name..." 
          value={searchQuery} 
          onChange={handleSearchChange} 
          className="search-input"
        />
        <div className='genre-select-wrapper'>
          <select value={selectedGenre} onChange={handleGenreChange} className="genre-select">
            <option value="">Select Genre</option>
            <option value="RPG">RPG</option>
            <option value="FPS">FPS</option>
            <option value="MOBA">MOBA</option>
            <option value="Adventure">Adventure</option>
            <option value="Sports">Sports</option>
            <option value="Simulation">Simulation</option>
            <option value="RTS">RTS</option>
            <option value="Sandbox">Sandbox</option>
          </select>
        </div>

        <div className="video-games-container">
          {videoGames.length > 0 ? (
            videoGames.map((game) => (
              <div key={game.id} className="video-game-card">
                <h2>
                  <Link to={`/videogame/${game.id}`} className="video-game-title">
                    {game.name}
                  </Link>
                </h2>
                <p>Publisher: {game.publisher}</p>
                <p>Released: {game.released}</p>
                <p>Genre: {game.genre}</p>  
              </div>
            ))
          ) : (
            <p className="no-video-games-message" >There are no video games with such criteria.</p>
          )}
        </div>
      </div>
    </div>
  );
}

export default VideoGames;