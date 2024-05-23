export const getVideoGames = async () => {
    const accessToken = sessionStorage.getItem('accessToken'); // Retrieve the access token from session storage
    try {
      const response = await fetch('http://localhost:8080/videogame/getAll', {
        method: 'GET',
        headers: {
          'Authorization': `Bearer ${accessToken}`,
          'Content-Type': 'application/json',
        },
      });
  
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
  
      const data = await response.json();
      return data;
    } catch (error) {
      console.error('Fetching error: ', error);
      return [];
    }
  };