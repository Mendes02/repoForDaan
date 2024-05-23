import React, { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import Navbar from '../Components/Navbar/Navbar';
import getRatingsFromPost from '../APILayer/getRatingsFromPost';
import savePostRating from '../APILayer/savePostRating';
import { FaThumbsUp, FaThumbsDown, FaRegComment } from 'react-icons/fa';
import { jwtDecode } from 'jwt-decode';
import createForumPostComment from '../APILayer/createForumPostComment';
import getForumPostComments from '../APILayer/getForumPostComments';
// ... other imports as necessary ...

function ForumPostPage() {
  const [post, setPost] = useState(null);
  const [commentContent, setCommentContent] = useState('');
  const [comments, setComments] = useState([]);
  const { forumPostId } = useParams();
  // Assuming the `userId` is retrieved from the JWT token stored in session storage
  const accessToken = sessionStorage.getItem("accessToken");
  const decodedToken = jwtDecode(accessToken);
  const userId = decodedToken.userId;
  const fetchPostDetails = async () => {
    try {
      const postDetails = await getRatingsFromPost(forumPostId);
      setPost(postDetails);
    } catch (error) {
      console.error('Failed to fetch post details:', error);
    }
  };

  useEffect(() => {
    if (forumPostId) {
      fetchPostDetails();
      fetchComments();
    }
  }, [forumPostId]);

  const handleRating = async (rateValue) => {
    await savePostRating(userId, rateValue, forumPostId);
    console.log('Saved rating for post:', forumPostId);
    await updatePostRatings();
  };

  const fetchComments = async () => {
    try {
      const fetchedComments = await getForumPostComments(forumPostId);
      setComments(fetchedComments);
    } catch (error) {
      console.error('Failed to fetch comments:', error);
    }
  };

  const handleCommentSubmit = async () => {
    // Prevent submission if the comment is empty
    if (!commentContent.trim()) {
      alert('Please enter a comment before submitting.');
      return;
    }
    
    try {
      await createForumPostComment(forumPostId, commentContent);
      console.log('Comment submitted successfully');
  
      // Clear the comment field
      setCommentContent('');
  
      // Fetch the updated comments
      await fetchComments();
    } catch (error) {
      console.error('Failed to submit comment:', error);
      // Handle the error (e.g., show an error message to the user)
    }
  };
  

  const updatePostRatings = async () => {
    try {
      const updatedRatings = await getRatingsFromPost(forumPostId);
      setPost({ ...post, ...updatedRatings });
    } catch (error) {
      console.error('Error updating post ratings:', error);
    }
  };

  // Ensure that post is not null before trying to render it
  if (!post) {
    return <div>Loading...</div>; // or any other loading state
  }

  return (
    <div>
      <Navbar/>
      <div className='postPageBackground'>
      <div className="review-card">
        <h3>
          <Link to={`/userProfile/${post.user.id}`} className="post-title">
            {post.user.username}
          </Link>
        </h3>
        <p>{post.postContent}</p>
        <div className="review-actions">
          <button className="like-btn" onClick={() => handleRating(true)}>
            <FaThumbsUp />
            {post.positiveRatings}
          </button>
          <button className="dislike-btn" onClick={() => handleRating(false)}>
            <FaThumbsDown />
            {post.negativeRatings}
          </button>
          <Link to={`/forumPost/${forumPostId}`} className="comment-btn">
            <FaRegComment />
            {post.comments}
          </Link>
        </div>
      </div>
      <div className="comment-form">
        <textarea
          value={commentContent}
          onChange={(e) => setCommentContent(e.target.value)}
          placeholder="Write your comment here..."
          className="comment-textarea"
        />
        <button
          onClick={handleCommentSubmit}
          className="submit-comment-btn"
        >
          Submit Comment
        </button>
      </div>
      <div className="comments-section">
      {comments.map((comment) => (
        <div key={comment.id} className="comment-card">
          <p>
          <h3><Link to={`/userProfile/${post.user.id}`} className="post-title">
            {comment.submittedBy.username}
          </Link> 
          </h3>
          {comment.commentContent}
          </p>
          <p className="comment-date">{comment.publishedDate}</p>
        </div>
      ))}
    </div>
      </div>
    </div>
  );
}

export default ForumPostPage;