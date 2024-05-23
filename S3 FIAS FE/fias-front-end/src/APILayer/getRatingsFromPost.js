const getRatingsFromPost = async (postId) => {
    const url = `http://localhost:8080/forumPost/${postId}`;
    const accessToken = sessionStorage.getItem('accessToken');
  
    const requestOptions = {
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${accessToken}`,
        'Content-Type': 'application/json'
      }
    };
  
    try {
      const response = await fetch(url, requestOptions);
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      return response.json(); // Parses the JSON response into a JavaScript object
    } catch (error) {
      console.error('There was a problem with the fetch operation:', error);
      throw error;
    }
  };

export default getRatingsFromPost;