import React, { useEffect } from 'react';
import { Link } from 'react-router-dom';

const Logout = () => {

    useEffect(() => {
        sessionStorage.removeItem("accessToken");
        window.location.href = '/';
        });
  return (
    <div></div>
  );
};

export default Logout;