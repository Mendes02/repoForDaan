import React, { useState } from 'react';
import { useParams } from 'react-router-dom';
import Navbar from '../Components/Navbar/Navbar';
import { jwtDecode } from 'jwt-decode';
import createVideoGameReview from "../APILayer/createVideoGameReview";

function CreateReview() {
  const { videoGameId } = useParams();
  const [reviewContent, setReviewContent] = useState('');
  const [reviewValue, setReviewValue] = useState(null);

  const accessToken = sessionStorage.getItem("accessToken");
  const decodedToken = jwtDecode(accessToken);
  const userId = decodedToken.userId;

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (reviewValue === null) {
      alert('Please select Like or Dislike.');
      return;
    }
    await createVideoGameReview(videoGameId, userId, reviewContent, reviewValue);
  };

  return (
    <div className="create-review-page">
      <Navbar/>
      <div className='background-review'>
        <h1 className="create-review-title">Create Review</h1>
        <form onSubmit={handleSubmit} className="create-review-form">
          <div className="form-group">
            <label htmlFor="reviewContent" className="form-label">Review Content:</label>
            <textarea
              id="reviewContent"
              value={reviewContent}
              onChange={(e) => setReviewContent(e.target.value)}
              className="review-textarea"
              required
            />
          </div>
          <div className="review-value-group">
            <label className="review-value-option">
              <input
                type="radio"
                name="reviewValue"
                value="like"
                checked={reviewValue === true}
                onChange={() => setReviewValue(true)}
                className="review-value-input"
              />
              Like
            </label>
            <label className="review-value-option">
              <input
                type="radio"
                name="reviewValue"
                value="dislike"
                checked={reviewValue === false}
                onChange={() => setReviewValue(false)}
                className="review-value-input"
              />
              Dislike
            </label>
          </div>
          <button type="submit" className="submit-review-btn">Submit Review</button>
        </form>
      </div>
    </div>
  );
}

export default CreateReview;
