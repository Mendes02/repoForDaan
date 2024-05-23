async function getFriends(userId) {
    const accessToken = sessionStorage.getItem('accessToken'); // Retrieve the access token from session storage
  
    try {
      const response = await fetch(`http://localhost:8080/friendships/${userId}`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${accessToken}` // Include the bearer token in the Authorization header
        }
      });
  
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`); // Throw an error if the response is not ok
      }
  
      const friendships = await response.json(); // Parse the JSON response
      return friendships; // Return the array of friends
    } catch (error) {
      console.error('Error fetching friendships:', error); // Log any errors to the console
    }
  }
  export default getFriends;