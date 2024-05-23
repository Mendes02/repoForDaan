export const getVideoGameReviews = async (videoGameId) => {
    const accessToken = sessionStorage.getItem('accessToken');
    try {
      const response = await fetch(`http://localhost:8080/videogameReview/${videoGameId}`, {
        method: 'GET',
        headers: {
          'Authorization': `Bearer ${accessToken}`,
        },
      });
  
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
  
      return await response.json();
    } catch (error) {
      console.error('Fetching error:', error);
    }
  };