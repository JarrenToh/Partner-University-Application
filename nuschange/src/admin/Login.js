import React, { useState, useContext } from 'react';
import { useNavigate } from 'react-router-dom';

import API from '../util/API';
import { LoginStyles } from '../student/login/LoginStyles';
import { AuthContext } from '../../src/AuthContext';

import { Helmet } from 'react-helmet';

const Login = () => {

  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const { loginAdmin } = useContext(AuthContext);

  const handleUsernameChange = (e) => {
    setUsername(e.target.value);
  };

  const handlePasswordChange = (e) => {
    setPassword(e.target.value);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await API.post('/admin/login', {
        username,
        password,
      });

      if (response.status === 200) {
        const admin = response.data;
        const userGroup = admin.userGroupEnum === "SYSTEM_SUPPORT" ? "systemSupportAdmin" : "userSupportAdmin";
        loginAdmin(admin);
        navigate(`/admin/${userGroup}/main`);
      }
    } catch (error) {
      alert("Invalid credentials");
    }
  };

  return (
    <div className="form" style={LoginStyles.formContainerStyle}>
      <Helmet>
        <title>Login</title>
      </Helmet>
      <div className="card card-primary" style={LoginStyles.formStyle}>
        <div className="card-header">
          <h3 className="card-title">NUSchange Admin</h3>
        </div>
        <form onSubmit={handleSubmit}>
          <div className="card-body">
            <div className="form-group" style={LoginStyles.formGroupStyle}>
              <label htmlFor="exampleInputEmail1">Username</label>
              <input type="username" className="form-control" id="exampleInputEmail1" placeholder="Enter username" onChange={handleUsernameChange} />
            </div>
            <div className="form-group" style={LoginStyles.formGroupStyle}>
              <label htmlFor="exampleInputPassword1">Password</label>
              <input type="password" className="form-control" id="exampleInputPassword1" placeholder="Password" onChange={handlePasswordChange} />
            </div>
          </div>
          <div className="card-footer">
            <button type="submit" className="btn btn-primary">Login</button>
          </div>
        </form>
      </div>
    </div>
  )
}

export default Login;