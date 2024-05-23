const getVideoGameById = async (videoGameId) => {
  try {
    const accessToken = sessionStorage.getItem('accessToken');

    if (!accessToken) {
      throw new Error('Access token not found');
    }

    const headers = {
      'Authorization': `Bearer ${accessToken}`,
      'Content-Type': 'application/json', // Assuming the content type is JSON
    };

    const response = await fetch(`http://localhost:8080/videogame/${videoGameId}`, {
      method: 'GET',
      headers: headers,
    });

    if (!response.ok) {
      throw new Error('Network response was not ok');
    }

    const gameData = await response.json();
    return gameData;
  } catch (error) {
    console.error('Error fetching video game:', error);
    throw error; // Propagate the error to the caller
  }
};

export default getVideoGameById;