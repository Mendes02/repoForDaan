const createPost = async (forumId, submittedById, postContent) => {
    const url = 'http://localhost:8080/forumPost';
    const accessToken = sessionStorage.getItem('accessToken');
  
    const requestOptions = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${accessToken}`
      },
      body: JSON.stringify({
        forumId,
        submittedById,
        postContent
      })
    };
  
    try {
      const response = await fetch(url, requestOptions);
      if (!response.ok) {
        return false;
      }
        return true;
    } catch (error) {
      console.error('There was a problem with the fetch operation:', error);
      throw error;
    }
  };
  export default createPost;