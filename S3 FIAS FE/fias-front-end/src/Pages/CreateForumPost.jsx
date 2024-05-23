import React, { useState } from 'react';
import { useParams } from 'react-router-dom';
import Navbar from '../Components/Navbar/Navbar';
import { jwtDecode } from 'jwt-decode';
import createPost from '../APILayer/createPost';

function CreateForumPost() {
  const { forumId } = useParams();
  const [postContent, setPostContent] = useState('');


  const accessToken = sessionStorage.getItem("accessToken");

  const decodedToken = jwtDecode(accessToken);
  const userId = decodedToken.userId;

  const handleSubmit = async (e) => {
    e.preventDefault();
     await createPost(forumId, userId, postContent);
     window.location.href = `/forums/${forumId}`;
  };

  return (
    <div className="create-review-page">
    <Navbar/>
    <div className='background-review'>
    <h1 className="create-review-title">Create Post</h1>
    <form onSubmit={handleSubmit} className="create-review-form">
      <div className="form-group">
        <label htmlFor="reviewContent" className="form-label">Post Content:</label>
        <textarea
          id="reviewContent"
          value={postContent}
          onChange={(e) => setPostContent(e.target.value)}
          className="review-textarea"
          required
        />
      </div>
      <button onClick={handleSubmit} type="submit" className="submit-review-btn">Create</button>
    </form>
    </div>
  </div>
  );
}

export default CreateForumPost;