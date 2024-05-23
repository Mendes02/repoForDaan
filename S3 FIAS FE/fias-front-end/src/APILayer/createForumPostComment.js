const createForumPostComment = async (postId, commentContent) => {
    const url = 'http://localhost:8080/forumPostComment';
    const accessToken = sessionStorage.getItem('accessToken'); // Retrieve the access token from session storage
  
    const requestOptions = {
      method: 'POST',
      headers: {
        'Authorization': `Bearer ${accessToken}`, // Include the bearer token in the Authorization header
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        postId: postId,
        commentContent: commentContent
      })
    };
  
    try {
      const response = await fetch(url, requestOptions);
      if (response.status === 200) {
        console.log('Comment created successfully');
        // Handle the success (e.g., clear the comment form or update the UI)
      } else if (response.status === 404) {
        console.error('Post not found');
        // Handle the post not found error
      } else {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
    } catch (error) {
      console.error('There was a problem with the fetch operation:', error);
      // Handle any other errors
    }
  };
  
  export default createForumPostComment;
  
  