import React, { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import "../Pages/static/App.css";
import Navbar from '../Components/Navbar/Navbar';
import getForumPosts from '../APILayer/getForumPosts';
import { FaThumbsUp, FaThumbsDown, FaRegComment } from 'react-icons/fa';
import savePostRating from '../APILayer/savePostRating';
import { jwtDecode } from 'jwt-decode';
import getRatingsFromPost from '../APILayer/getRatingsFromPost';


function ForumPage() {
  const [posts, setPosts] = useState([]);
  const [videoGameName, setVideoGameName] = useState('');
  const { forumId } = useParams();
  const accessToken = sessionStorage.getItem("accessToken");
  const decodedToken = jwtDecode(accessToken);
  const userId = decodedToken.userId;

  useEffect(() => {
    getForumPosts(forumId).then(data => {
      if(data && data.length > 0) {
        setPosts(data);
        setVideoGameName(data[0].forum.videoGame.name);
        if(data[0].forum.videoGame.name === null){
          setVideoGameName("Not found");
        }
      }
    }).catch(error => console.error(error));
  }, [forumId]);

  const handleRating = async (postId,rateValue) => {
    await savePostRating(userId,rateValue,postId)
    console.log('Saved rating for post:', postId);
    await updatePostRatings(postId);
  };

  const updatePostRatings = async (postId) => {
    try {
      const updatedRatings = await getRatingsFromPost(postId);
      setPosts(currentPosts => 
        currentPosts.map(post => 
          post.id === postId ? { ...post, ...updatedRatings } : post
        )
      );
    } catch (error) {
      console.error('Error updating post ratings:', error);
    }
  };

  return (
    <div>
      <Navbar/>
      <h2 className="reviews-title">Forum Posts for {videoGameName}</h2>
      <div className='background-wrapper'>
        <div className="reviews-container">
        {posts.map((post) => (
  <div key={post.id} className="review-card">
    <h3>
      <Link to={`/userProfile/${post.user.id}`} className="post-title">
        {post.user.username}
      </Link>
    </h3>
    <p>{post.postContent}</p>
    <div className="review-actions">
      <button className="like-btn" onClick={() => handleRating(post.id, true)}>
        <FaThumbsUp />
        {post.positiveRatings}
      </button>
      <button className="dislike-btn" onClick={() => handleRating(post.id, false)}>
        <FaThumbsDown />
        {post.negativeRatings}
      </button>
      <Link to={`/forumPost/${post.id}`} className="comment-btn">
        <FaRegComment />
        {post.comments}
      </Link>
    </div>
  </div>
))}

        </div>
      </div>
      <Link to={`/createPost/${forumId}`} className="create-review-btn">
          Create a Post
        </Link>
    </div>
  );
}

export default ForumPage;


