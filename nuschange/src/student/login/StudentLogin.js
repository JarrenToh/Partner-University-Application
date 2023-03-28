import React, { useState, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import API from '../../util/API';
import { LoginStyles } from './LoginStyles';
import { AuthContext } from './AuthContext';

const Login = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();
  const { login } = useContext(AuthContext);

  const handleUsernameChange = (e) => {
    setUsername(e.target.value);
  };

  const handlePasswordChange = (e) => {
    setPassword(e.target.value);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await API.post('/student/login', {
        username,
        password,
      });
      // assuming the server returns the student object after a successful login
      if (response.status === 200) {
        const student = response.data;
        login(student); // Call login function with student object from AuthContext
        alert('You have logged in successfully');
        navigate('/home-page');
      } else {
        alert('Login failed. Please check your credentials and try again.');
      }
    } catch (error) {
      alert('Error logging in. Please try again.');
      console.error(error);
    }
  };

  return (
    <div className="form" style={LoginStyles.formContainerStyle}>
      <div className="card card-primary" style={LoginStyles.formStyle}>
        <div className="card-header">
          <h3 className="card-title">Student Login</h3>
        </div>
        <form onSubmit={handleSubmit}>
          <div className="card-body">
            <div className="form-group" style={LoginStyles.formGroupStyle}>
              <label htmlFor="exampleInputEmail1">Username</label>
              <input
                type="username"
                className="form-control"
                id="exampleInputEmail1"
                placeholder="Enter username"
                onChange={handleUsernameChange}
              />
            </div>
            <div className="form-group" style={LoginStyles.formGroupStyle}>
              <label htmlFor="exampleInputPassword1">Password</label>
              <input
                type="password"
                className="form-control"
                id="exampleInputPassword1"
                placeholder="Password"
                onChange={handlePasswordChange}
              />
            </div>
          </div>
          <div className="card-footer">
            <button type="submit" className="btn btn-primary">
              Submit
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default Login;
