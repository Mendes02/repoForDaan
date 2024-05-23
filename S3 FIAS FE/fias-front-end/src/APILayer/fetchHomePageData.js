export const fetchHomePageData = async (userId) => {
  try {
    const accessToken = sessionStorage.getItem('accessToken');
    const response = await fetch(`http://localhost:8080/homePage/${userId}`, {
      method: 'GET',
      headers: {
        Authorization: `Bearer ${accessToken}`,
        'Content-Type': 'application/json'
      }
    });

    if (!response.ok) {
      throw new Error('Network response was not ok');
    }

    return await response.json();
  } catch (error) {
    console.error('Fetch error:', error);
    throw error;
  }
};