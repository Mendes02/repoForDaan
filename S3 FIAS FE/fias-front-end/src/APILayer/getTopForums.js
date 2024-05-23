
const getTopForums = () => {
    const token = sessionStorage.getItem('accessToken');
    if (!token) {
        console.error('No access token found in session storage');
        return Promise.reject('No access token found in session storage'); // Return a rejected promise
    }

    const requestOptions = {
        method: 'GET',
        headers: { 'Authorization': 'Bearer ' + token }
    };

    // Return the fetch promise chain
    return fetch('http://localhost:8080/forum/topForums', requestOptions)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json(); // This returns a promise
        })
        .catch(error => {
            console.error('Error fetching top forums:', error);
            return Promise.reject(error); // Return a rejected promise
        });
};

export default getTopForums;
