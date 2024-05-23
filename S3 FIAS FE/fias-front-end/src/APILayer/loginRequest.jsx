function makeLoginRequest(email, password) {
    const userData = {
      email: email,
      password: password,
    };
  
    return fetch('http://localhost:8080/tokens', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(userData),
    })
      .then(async (response) => {
        if (!response.ok) {
          const errorData = await response.json();
          throw new Error(errorData.error);
        }
        return response.json();
      })
      .then((data) => {
        const token = data.accessToken;
        sessionStorage.setItem('accessToken', token);
        return token;
      })
      .catch((error) => {
        console.error('Error logging in:', error);
        throw error;
      });
  }
  
  export default makeLoginRequest;