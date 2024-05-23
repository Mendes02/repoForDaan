import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { jwtDecode } from 'jwt-decode';
import { getVideoGameReviews } from "../APILayer/getVideoGameReviews";
import getVideoGameById from "../APILayer/getVideoGame";
import "../Pages/static/App.css"; 
import Navbar from '../Components/Navbar/Navbar';
import { FaThumbsUp, FaThumbsDown, FaEllipsisV } from 'react-icons/fa';
import deleteVideoGameReview from '../APILayer/deleteOwnVideoGameReview';
import { Link } from 'react-router-dom';
import saveReviewRating from '../APILayer/saveReviewRating';
import getRatingsFromReview from '../APILayer/getRatingsFromReview';

function VideoGameReviews() {
  const [reviews, setReviews] = useState([]);
  const [gameName, setGameName] = useState('');
  const [userId, setUserId] = useState(null);
  const [userRoles, setUserRoles] = useState([]);
  const { videoGameId } = useParams();
  const [showMenu, setShowMenu] = useState(null);

  useEffect(() => {
    getVideoGameReviews(videoGameId).then(data => {
      if(data) {
        setReviews(data);
      }
      const closeMenu = () => setShowMenu(null);
      document.addEventListener('click', closeMenu);
      return () => document.removeEventListener('click', closeMenu);
    });

    getVideoGameById(videoGameId).then(gameData => {
      if (gameData) {
        setGameName(gameData.name);
      }
    });

    const token = sessionStorage.getItem('accessToken');
    if (token) {
      const decodedToken = jwtDecode(token);
      setUserId(decodedToken.userId);
      setUserRoles(decodedToken.roles);
    }
  }, [videoGameId]);

  const handleDelete = (reviewId) => {
    deleteVideoGameReview(reviewId);
    setReviews(prevReviews => prevReviews.filter(review => review.id !== reviewId));
    setShowMenu(null);
  };

  const handleMenuClick = (e, reviewId) => {
    e.stopPropagation();
    setShowMenu(showMenu === reviewId ? null : reviewId);
  };

  const handleCreateReview = () => {
    console.log('Create a review button clicked');
  };

  const  saveRating = async (reviewId,rateValue) => {
    try {
      const success = await saveReviewRating(userId, rateValue, reviewId);
      if(success){
        console.log("Success saving rating.");
      }
      else{
        console.log("Error saving rating");
      }
      await updateReviewRatings(reviewId)

    } catch (error) {
      console.error('Error saving rating:', error);
    }
  }

  const updateReviewRatings = async (reviewId) => {
    try {
      const updatedRatings = await getRatingsFromReview(reviewId);
      setReviews(currentReviews => 
        currentReviews.map(review => 
          review.id === reviewId ? { ...review, ...updatedRatings } : review
        )
      );
    } catch (error) {
      console.error('Error updating review ratings:', error);
    }
  };

  const isModerator = userRoles.includes('Moderator');

  return (
    <div>
      <Navbar/>
      <h2 className="reviews-title">Reviews for {gameName}</h2>
      <div className='background-wrapper'>
      <button className="create-review-btn" onClick={handleCreateReview}>Create a Review</button>
        <div className="reviews-container">
          {reviews.map((review) => (
            <div key={review.id} className="review-card">
              <Link to={`/userProfile/${review.reviewedBy.id}`} className="video-game-title"><h3>{review.reviewedBy.username}</h3></Link>
              {review.rateValue ? <FaThumbsUp className="thumbs-up-icon" title='This user liked this video game'/> : <FaThumbsDown className="thumbs-down-icon" title='This user disliked this video game'/>}
              <p>{review.reviewContent}</p>
              <div className="review-actions">
                <button className="like-btn" onClick={() => saveRating(review.id, true)}><FaThumbsUp />{review.positiveRatings}</button>
                <button className="dislike-btn" onClick={() => saveRating(review.id, false)}><FaThumbsDown />{review.negativeRatings}</button>
                {(userId === review.reviewedBy.id || isModerator) && (
                  <div className="review-menu" onClick={(e) => handleMenuClick(e, review.id)}>
                    <FaEllipsisV />
                    {showMenu === review.id && (
                      <div className="menu-content show-menu">
                        <button onClick={() => handleDelete(review.id)}>Delete review</button>
                      </div>
                    )}
                  </div>
                )}
              </div>
            </div>
          ))}
        </div>

      </div>
      <Link to={`/videogameReviews/create/${videoGameId}`} className="create-review-btn">
          Create a Review
      </Link>
    </div>
  );
}

export default VideoGameReviews;

