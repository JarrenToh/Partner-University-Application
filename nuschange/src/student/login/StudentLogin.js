import React, { useState, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import API from '../../util/API';
import { LoginStyles } from './LoginStyles';
import { AuthContext } from '../../AuthContext';
import NavbarComp from '../../student/components/NavbarComp';

import {
  Button,
  Form,
  FormGroup,
  Input,
  Label,
  Card,
  CardHeader,
  CardBody,
  CardFooter,
  Alert,
} from 'reactstrap';

const Login = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();
  const { login } = useContext(AuthContext);
  const [alertVisible, setAlertVisible] = useState(false);
  const [alertMessage, setAlertMessage] = useState('');
  const [alertType, setAlertType] = useState('danger');

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
        setAlertType('success');
        setAlertMessage('You have logged in successfully');
        setAlertVisible(true);
        setTimeout(() => {
          navigate('/student/home-page');
        }, 2000);
      } else {
        setAlertType('danger');
        setAlertMessage(
          'Login failed. Please check your credentials and try again.'
        );
        setAlertVisible(true);
      }
    } catch (error) {
      setAlertType('danger');
      setAlertMessage('Error logging in. Please try again.');
      setAlertVisible(true);
      console.error(error);
    }
  };

    const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [user, setUser] = useState(null);

  return (
    <div className='wrapper'>
       <NavbarComp isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn} user={user} />
    <div
      className="form-container"
      style={{
        ...LoginStyles.formContainerStyle,
        backgroundImage: `url('https://cde.nus.edu.sg/wp-content/uploads/2021/08/article-image-2048x1152-1.png')`,
        backgroundSize: 'cover',
        backgroundPosition: 'center',
        minHeight: '100vh',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        borderRadius: '2%', // Set border radius to 50% to create curved edges
        //overflow: 'hidden', // Hide anything that overflows the container
      }}
    >
     
      <Card
        className="form"
        style={{
          ...LoginStyles.formStyle,
          backgroundColor: 'rgba(255,255,255,0.7)',
          boxShadow: '0 0 10px rgba(0,0,0,0.3)',
          maxWidth: '700px', // Set the maximum width of the card to 400px
        }}
      >
        <CardHeader>
          <h3 className="card-title">Student Login</h3>
        </CardHeader>
        <Form onSubmit={handleSubmit}>
          <CardBody>
            <FormGroup>
              <Label for="username">Username</Label>
              <Input
                type="text"
                name="username"
                id="username"
                placeholder="Enter username"
                value={username}
                onChange={handleUsernameChange}
              />
            </FormGroup>
            <FormGroup>
              <Label for="password">Password</Label>
              <Input
                type="password"
                name="password"
                id="password"
                placeholder="Enter password"
                value={password}
                onChange={handlePasswordChange}
              />
            </FormGroup>
          </CardBody>
          <CardFooter>
            <Button color="primary" type="submit">
              Submit
            </Button>
          </CardFooter>
        </Form>
      </Card>
      {alertVisible && (
        <Alert color={alertType} toggle={() => setAlertVisible(false)}>
          {alertMessage}
        </Alert>
      )}
    </div>
    </div>
  );
};

export default Login;