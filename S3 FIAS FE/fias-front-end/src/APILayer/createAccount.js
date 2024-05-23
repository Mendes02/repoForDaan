
const createNewAccount = (email,username,password) => {
    const userData = {
      email: email,
      username: username,
      password: password
    };

    fetch('http://localhost:8080/user', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(userData)
    })
    .then(response => response.json())
    .then(data => {
      console.log('Account created successfully:', data);

    })
    .catch(error => {
      console.error('Error creating account:', error);
    });
  };
  export default createNewAccount