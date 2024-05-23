// getForumPosts.js
const getForumPosts = (forumId) => {
  const token = sessionStorage.getItem('accessToken');
  if (!token) {
    console.error('No access token found in session storage');
    return Promise.reject('No access token found in session storage');
  }

  const requestOptions = {
    method: 'GET',
    headers: { 'Authorization': `Bearer ${token}` },
  };

  return fetch(`http://localhost:8080/forumPost/byForum/${forumId}`, requestOptions)
    .then(response => {
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      return response.json();
    })
    .catch(error => {
      console.error('Error fetching forum posts:', error);
      return Promise.reject(error);
    });
};

export default getForumPosts;
