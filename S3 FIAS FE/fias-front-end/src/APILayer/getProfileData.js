const getProfileData = async (userId) => {
    try {
      // Retrieve the access token from session storage
      const accessToken = sessionStorage.getItem("accessToken");
  
      // Making a GET request to the server
      const response = await fetch(`http://localhost:8080/user/profileData/${userId}`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${accessToken}`, // Attach the bearer token
        },
      });
  
      // Check if the response is ok (status in the range 200-299)
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
  
      // Parse the response body as JSON
      const data = await response.json();
      return data;
    } catch (error) {
      console.error('There was a problem fetching the profile data:', error);
      throw error; // Rethrow the error for the caller to handle
    }
  };
  
  export default getProfileData;