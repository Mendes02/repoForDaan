const savePostRating = async (ratedById, rateValue, forumPostId) => {
    const url = 'http://localhost:8080/forumPostRatings';
    const accessToken = sessionStorage.getItem('accessToken');
  
    const requestOptions = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${accessToken}`
      },
      body: JSON.stringify({
        ratedById,
        rateValue,
        forumPostId
      })
    };
  
    try {
      const response = await fetch(url, requestOptions);
      if (!response.ok) {
        const errorText = await response.text(); // Optional: Get more error info if available
        throw new Error(`Network response was not ok: ${response.status}. ${errorText}`);
      }
  
      // If the response is ok (201), return a success indicator or message
      return { success: true };
    } catch (error) {
      console.error('There was a problem with the fetch operation:', error);
      throw error;
    }
  };
  
  export default savePostRating;
  