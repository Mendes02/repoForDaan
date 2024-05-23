import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom'; // Import useNavigate
import createAccountAPI from '../APILayer/createAccount';
import './static/App.css';

function CreateAccount() {
  const [email, setEmail] = useState('');
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState(''); // State to track errors

  const navigate = useNavigate(); // useNavigate hook for redirection

  const handleEmailChange = (event) => {
    setEmail(event.target.value);
  };

  const handleUsernameChange = (event) => {
    setUsername(event.target.value);
  };

  const handlePasswordChange = (event) => {
    setPassword(event.target.value);
  };

  const handleCreateAccount = async () => {
    try {
      const response = await createAccountAPI(email, username, password);
      // Assuming the response contains the user ID on successful account creation
      if (response && response.id) {
        navigate('/'); // Redirect to home page
      }
    } catch (err) {
      setError('There was an error creating your account.'); // Set error message
    }
  };

  return (
    <div className='form'>
      <input type='text' className='textbox' placeholder='Email' value={email} onChange={handleEmailChange} />
      <br />
      <input type='text' className='textbox' placeholder='Username' value={username} onChange={handleUsernameChange} />
      <br />
      <input type='password' className='textbox' placeholder='Password' value={password} onChange={handlePasswordChange} />
      <br />
      <button className='createUserButton' onClick={handleCreateAccount}>Create new account</button>
      {error && <p className='error-message'>{error}</p>} {/* Display error message */}
    </div>
  );
}

export default CreateAccount;

