import React from 'react';

const deleteVideoGameReview = async (videogameReviewId) => {
  const accessToken = sessionStorage.getItem('accessToken');
  if (!accessToken) {
    console.error('No access token found in session storage.');
    return;
  }

  const requestBody = {
    accessToken: accessToken,
    videogameReviewId: videogameReviewId
  };

  try {
    const response = await fetch('http://localhost:8080/videogameReview', {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${accessToken}` 
      },
      body: JSON.stringify(requestBody)
    });

    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }

    const data = await response.json();
    console.log('Success:', data);

  } catch (error) {
    console.error('Error:', error);
  }
};

export default deleteVideoGameReview;
