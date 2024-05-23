const getFriendRequests = async (userId) => {
    try {
        const token = sessionStorage.getItem("accessToken");
      const response = await fetch(`http://localhost:8080/friendships/requests/${userId}`, {
        method: 'GET',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
      });
  
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
  
      const data = await response.json();
      return data;
    } catch (error) {
      console.error('Error fetching friend requests:', error);
    }
  };
  
  export default getFriendRequests;