const saveReviewRating = async (ratedById, rateValue, videogameReviewId) => {
    // Retrieve the access token from session storage
    const accessToken = sessionStorage.getItem("accessToken");
  
    // Prepare the request body
    const requestBody = {
      ratedById,
      rateValue,
      videogameReviewId,
    };
  
    try {
      // Making a POST request using fetch
      const response = await fetch('http://localhost:8080/reviewRating', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${accessToken}`, // Attach the bearer token
        },
        body: JSON.stringify(requestBody),
      });
  
      // Check if the response status code is 201 (Created)
      if (response.status === 201) {
        console.log('Review rating saved successfully.');
        return true; // Or any other appropriate value or action
      } else if (response.status === 500) {
        console.error('Server error occurred.');
        return false; // Or throw an error or another appropriate action
      } else {
        // Handle any other status codes if necessary
        console.error(`Unexpected status code: ${response.status}`);
        return false; // Or any other appropriate action
      }
    } catch (error) {
      console.error('There was a problem saving the review rating:', error);
      throw error; // Re-throw the error to be handled by the caller
    }
  };
  export default saveReviewRating;
  