const createFriendshipRequest = async (requesterId, requestedId) => {
    // Retrieve the access token from session storage
    const accessToken = sessionStorage.getItem("accessToken");
  
    // Making a POST request to the server
    try {
      const response = await fetch(`http://localhost:8080/friendships/create/${requesterId}/${requestedId}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${accessToken}`, // Attach the bearer token
        },
      });
  
      // Check if the response is ok (status in the range 200-299)
      if (response.ok) {
        // The request was successful
        console.log('Friendship request created successfully');
      } else if (response.status === 401) {
        // Handle unauthorized error
        console.error('Unauthorized - Possibly invalid token');
      } else if (response.status === 404) {
        // Handle not found error
        console.error('User not found');
      } else if (response.status === 400) {
        // Handle bad request error
        console.error('Bad request - Possibly already friends or request already sent');
      } else {
        // Handle other errors
        const errorData = await response.text();
        console.error('Failed to create friendship request:', errorData);
      }
    } catch (error) {
      // Handle network errors or other exceptions
      console.error('Network error or other exception:', error);
    }
  };

export default createFriendshipRequest;