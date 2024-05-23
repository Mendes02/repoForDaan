const getForumPostComments = async (forumPostId) => {
    const url = `http://localhost:8080/forumPostComment/${forumPostId}`;
    const accessToken = sessionStorage.getItem('accessToken');

    const requestOptions = {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${accessToken}`,
            'Content-Type': 'application/json'
        }
    };

    try {
        const response = await fetch(url, requestOptions);
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return await response.json(); // Parses the JSON response into a JavaScript object
    } catch (error) {
        console.error('There was a problem with the fetch operation:', error);
        throw error;
    }
};

export default getForumPostComments;
