import React, { useState } from 'react';
import '../App';
import makeLoginRequest from '../APILayer/loginRequest';
import { Link } from 'react-router-dom';
function Login() {

  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const handleEmailChange = (event) => {
    setEmail(event.target.value);
  };

  const handlePasswordChange = (event) => {
    setPassword(event.target.value);
  };

  async function OnLogin(event) {
    event.preventDefault();
    try {
      const token = await makeLoginRequest(email, password);
      if (token) {
        window.location.href = '/home-page';
      } else {
        console.log('Invalid credentials');
      }
    } catch (error) {
      console.error('Error:', error);
    }
  }

  return (
    <div className='form'>
      <input type='text' className='textbox' placeholder='Email' value={email} onChange={handleEmailChange} />
      <br />
      <br />
      <input type='password' className='textbox' placeholder='Password' value={password} onChange={handlePasswordChange} />
      <br />
      <button className='createUserButton' onClick={OnLogin}>Login</button>
      <Link to={"/create-account"} className="createUserButton">
          Create account
        </Link>
    </div>
  );
}

export default Login;