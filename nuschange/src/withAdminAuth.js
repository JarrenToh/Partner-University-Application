import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

const withAdminAuth = (WrappedComponent) => {
  const WithAdminAuthComponent = (props) => {
    const navigate = useNavigate();

    useEffect(() => {
      if (localStorage.getItem("loggedInAdmin") === null) {
        navigate('../login');
      }
    }, [navigate]);

    return localStorage.getItem("loggedInAdmin") !== null ? <WrappedComponent {...props} /> : null;
  };

  return WithAdminAuthComponent;
};

export default withAdminAuth;
