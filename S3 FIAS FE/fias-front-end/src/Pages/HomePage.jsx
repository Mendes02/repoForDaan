import React, { useState, useEffect } from 'react';
import Navbar from '../Components/Navbar/Navbar';
import '../Pages/static/App.css';
import { jwtDecode } from 'jwt-decode';
import { fetchHomePageData } from '../APILayer/fetchHomePageData';
import { FaThumbsUp, FaThumbsDown, FaEllipsisV, FaRegComment } from 'react-icons/fa';
import { Link } from 'react-router-dom';
import savePostRating from '../APILayer/savePostRating';
import getRatingsFromPost from '../APILayer/getRatingsFromPost';
import saveReviewRating from '../APILayer/saveReviewRating';
import getRatingsFromReview from '../APILayer/getRatingsFromReview';


const HomePage = () => {
  const [homePageData, setHomePageData] = useState({
    suggestedForumPosts: [],
    suggestedVideoGameReviews: [],
    trendingVideoGames: []
  });

  useEffect(() => {
    const accessToken = sessionStorage.getItem("accessToken");
    if (accessToken) {
      const decodedToken = jwtDecode(accessToken);
      const userId = decodedToken.userId;
      fetchHomePageData(userId)
        .then(data => setHomePageData(data))
        .catch(error => console.error('Error fetching homepage data:', error));
    } else {
      window.location.href = '/login';
    }
  }, []);
  const accessToken = sessionStorage.getItem("accessToken");
  const decodedToken = jwtDecode(accessToken);
  const userId = decodedToken.userId;

  const handlePostRating = async (postId,rateValue) => {
    await savePostRating(userId,rateValue,postId)
    console.log('Saved rating for post:', postId);
    await updatePostRatings(postId);
  };

  const updatePostRatings = async (postId) => {
    try {
      const updatedRatings = await getRatingsFromPost(postId);
      setHomePageData(currentData => ({
        ...currentData,
        suggestedForumPosts: currentData.suggestedForumPosts.map(post => 
          post.id === postId ? { ...post, ...updatedRatings } : post
        )
      }));
    } catch (error) {
      console.error('Error updating post ratings:', error);
    }
  };

  const  createReviewRating = async (reviewId,rateValue) => {
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
      setHomePageData(currentData => ({
        ...currentData,
        suggestedVideoGameReviews: currentData.suggestedVideoGameReviews.map(review => 
          review.id === reviewId ? { ...review, ...updatedRatings } : review
        )
      }));
    } catch (error) {
      console.error('Error updating review ratings:', error);
    }
  };

  return (
<div className='homepage'>
  <Navbar />
  <div className="background-wrapper-hp">
    <div className='grid-container'>
      <div className="forum-posts-container">
        <h2 className="column-title-hp">Suggested Posts</h2>
        {homePageData.suggestedForumPosts.map(post => (
          <div key={post.id} className="review-card-hp">
          <Link to={`/userProfile/${post.user.id}`} className="post-title"> <h3>{post.user.username} -For {post.forum.videoGame.name} </h3></Link>
            <p>{post.postContent}</p>
            <div className="review-actions">
              <button className="like-btn" onClick={() => handlePostRating(post.id, true)}><FaThumbsUp /> {post.positiveRatings}</button>
              <button className="dislike-btn" onClick={() => handlePostRating(post.id, false)}><FaThumbsDown /> {post.negativeRatings}</button>
              <Link to={`/forumPost/${post.id}`} className="comment-btn">
              <FaRegComment />
              {post.comments}
            </Link>
            </div>
          </div>
        ))}
      </div>
      <div className="reviews-container-hp">
        <h2 className="column-title-hp">Suggested Reviews</h2>
        {homePageData.suggestedVideoGameReviews.map(review => (
          <div key={review.id} className="review-card-hp">
            <Link to={`/userProfile/${review.reviewedBy.id}`} className='post-title' ><h3>{review.reviewedBy.username} - For {review.videoGame.name}</h3></Link>
            <p>{review.reviewContent}</p>
            <div className="review-actions">
              <button className="like-btn" onClick={() => createReviewRating(review.id, true)}><FaThumbsUp /> {review.positiveRatings}</button>
              <button className="dislike-btn" onClick={() => createReviewRating(review.id, false)}><FaThumbsDown /> {review.negativeRatings}</button>
            </div>
          </div>
        ))}
      </div>
      <div className="trending-games-container">
        <h2 className="column-title-hp">Trending Video Games</h2>
        {homePageData.trendingVideoGames.map(game => (
          <div key={game.id} className="column-card">
            <h3>{game.name}</h3>
            <p>Publisher: {game.publisher}</p>
            <p>Release Date: {game.released}</p>
            <Link to={`/forums/${game.id}`} className="btn btn-forum">Forum</Link>
            <Link to={`/videogameReviews/${game.id}`} className="btn btn-reviews">Reviews</Link>
          </div>
        ))}
      </div>
    </div>
  </div>
</div>
  );
};

export default HomePage;