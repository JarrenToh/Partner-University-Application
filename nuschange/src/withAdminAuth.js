import React, { useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';

const withAdminAuth = (adminType) => (WrappedComponent) => {
  const WithAdminAuthComponent = (props) => {
    const navigate = useNavigate();
    const location = useLocation();

    useEffect(() => {
      const loggedInAdmin = localStorage.getItem("loggedInAdmin");

      if (loggedInAdmin === null) {
        navigate('../admin/login');
      } else {
        const parsedAdmin = JSON.parse(loggedInAdmin);
        const isUrlUserSupportAdmin = location.pathname.includes("userSupportAdmin");

        if (isUrlUserSupportAdmin && parsedAdmin.userGroupEnum !== "USER_SUPPORT") {
          navigate(`../admin/systemSupportAdmin/main`);
        } else if (!isUrlUserSupportAdmin && parsedAdmin.userGroupEnum !== "SYSTEM_SUPPORT") {
          navigate(`../admin/userSupportAdmin/main`);
        }
      }
    }, [navigate, location]);

    const loggedInAdmin = localStorage.getItem("loggedInAdmin");
    return loggedInAdmin !== null ? <WrappedComponent {...props} /> : null;
  };

  return WithAdminAuthComponent;
};

export default withAdminAuth;