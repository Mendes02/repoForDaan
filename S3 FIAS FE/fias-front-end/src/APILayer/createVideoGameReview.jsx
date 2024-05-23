async function createVideoGameReview(videoGameId, userId, reviewContent,rateValue) {
  const accessToken = sessionStorage.getItem('accessToken'); // Retrieve the access token from session storage

  try {
      const response = await fetch('http://localhost:8080/videogameReview', {
          method: 'POST',
          headers: {
              'Content-Type': 'application/json',
              'Authorization': `Bearer ${accessToken}`, // Use the bearer token for authorization
          },
          body: JSON.stringify({
              videoGameId: videoGameId,
              userId: userId,
              reviewContent: reviewContent,
              rateValue: rateValue
          })
      });

      if (response.status === 201) {
          console.log('Review created successfully.');
          console.log(videoGameId);
          window.location.href = `/videogameReviews/${videoGameId}`;
          return {};
      } else {
          // If the response status code is not 201, throw an error
          throw new Error(`HTTP Error Response: ${response.status} ${response.statusText}`);
      }
  } catch (error) {
      // Log the error and return null or handle as needed
      console.error('There was an error creating the review:', error);
      return null; // or throw error;
  }
}

export default createVideoGameReview;

  