import { createContext, useContext, useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import HomePage from './student/homepage/HomePage';
import Login from './admin/Login';
import { Button } from "reactstrap";
import './LandingPage.css';
import logo from './NUSChange-logoV3.png';

const App = () => {
  const [url, setUrl] = useState(window.location.pathname);

  const [showNavbar, setShowNavbar] = useState(false);

  return (

    <div className="form-container"
      style={{
        display: 'flex',
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'center',
        minHeight: '100vh',
        overflow: 'hidden',
      }}
    >

      <div
        className="background-image"
        style={{
          backgroundImage: `url('https://vafs.nus.edu.sg/adfs/portal/illustration/illustration.png?id=D8C820A074D7AA69EC4E14BF393CD988229E3FDFDD669BA88766CCB22BB5E078')`,
          backgroundSize: 'cover',
          minHeight: '100vh',
          backgroundPosition: 'left',
          width: '80%',
          height: '100%',
          marginRight: '1%',
        }}
      />

      <div>
        <div style={{ display: 'flex', justifyContent: 'center' }}>
          <img
            alt=""
            src={logo}
            width="300"
            height="100"
            className="d-inline-block align-top"
          />{' '}
        </div>
        <br />
        <br />
        <br />
        <h1>Welcome to the NUSChange Portal!</h1>
        <br />
        <br />
        <div style={{ display: 'flex', justifyContent: 'center' }}>
          <h3>Please select your portal:</h3>
        </div>
        <br />
        <br />
        <br />
        <br />
        <div className="button-container" style={{ display: 'flex', justifyContent: 'center' }}>
          <div>
            <p>Student Portal</p>
            <Button color="primary" className='Student' style={{ backgroundColor: `green`, textDecoration : 'none'}} onClick={setUrl}>
              <Link to="/student/home-page" className="text-white" style={{textDecoration : 'none'}}>Go to Student Portal</Link>
            </Button>
          </div>
          <div>
            <p>Admin Portal</p>
            <Button color="secondary" className='Admin' style={{ backgroundColor: `orange` }}>
              <Link to="/admin/login" className="text-white" style={{textDecoration : 'none'}}>Go to Admin Portal</Link>
            </Button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default App;
