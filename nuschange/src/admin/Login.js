import React, { useState } from 'react';

import API from '../util/API';
import { LoginStyles } from './userSupportAdmin/styles/LoginStyles';

const Login = ({ onLogin }) => {

  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

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

      // TODO: need to do redirect to main page
      alert("You have logged in successfully");
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div className="form" style={LoginStyles.formContainerStyle}>
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
            <button type="submit" className="btn btn-primary">Submit</button>
          </div>
        </form>
      </div>
    </div>
  )
}

export default Login;