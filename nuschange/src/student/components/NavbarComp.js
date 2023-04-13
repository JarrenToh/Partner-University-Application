import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import React, { useState, useEffect, useContext } from "react";
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';
import logo from './NUSChange-logoV3.png';
import './Navbar.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Button } from 'reactstrap';
import UniversityRankings from '../ranking/UniversityRankings';
import { AuthContext } from '../login/AuthContext';

const NavbarComp = () => {
  const API_URL = "http://localhost:8080/PU-war/webresources/pu";
  const [pus, setPUs] = useState([]);
  const { loggedInStudent, logout } = useContext(AuthContext);
  const [alertMessage, setAlertMessage] = useState('');
  const [alertVisible, setAlertVisible] = useState(false);
  const [alertType, setAlertType] = useState('danger');
  const [isLoggedOut, setIsLoggedOut] = useState(false);

  useEffect(() => {
    searchPUs("");
  }, []);

  const searchPUs = async (title) => {
    const response = await fetch(`${API_URL}`);
    const data = await response.json();
    console.log(data);
    setPUs(data);
  };

  const handleLogout = () => {
    setIsLoggedOut(true);
    setTimeout(() => {
      setIsLoggedOut(false);
      logout();
      setAlertType('success');
      setAlertMessage('You have logged out successfully');
      setAlertVisible(true);
    }, 1000); // Remove the alert message after 3 seconds
  };

  return (
    <Navbar bg="light" variant="light">
      <Navbar.Brand href="/student/home-page" className='navBrand'>
        <img
          alt=""
          src={logo}
          width="200"
          height="70"
          className="d-inline-block align-top"
        />{' '}
      </Navbar.Brand>
      <Navbar.Toggle aria-controls="navbarScroll" />
      <Navbar.Collapse id="basic-navbar-nav">
        <Nav className="me-auto">
          <Nav.Link href="/student/home-page" className='navLink'>Home</Nav.Link>
          <NavDropdown title="Ranking" id="basic-nav-dropdown" className='navLinkDropDown' >
            <NavDropdown.Item href="/student/university-rankings?search=">All Rankings</NavDropdown.Item>
            <NavDropdown.Divider />
            <NavDropdown.Item href="/student/university-rankings-country">Ranking by Country</NavDropdown.Item>
            <NavDropdown.Item href="/student/university-rankings-region">Ranking by Region</NavDropdown.Item>
          </NavDropdown>
          <Nav.Link href="/student/forum-topics/0" className='navLink'>Forum</Nav.Link>
          <NavDropdown title="Support" id="basic-nav-dropdown" className='navLinkDropDown' >
            <NavDropdown.Item href="/student/faq">FAQs</NavDropdown.Item>
            {loggedInStudent && (
              <>
                <NavDropdown.Item href="/student/enquiry">Submit an Enquiry</NavDropdown.Item>
                <NavDropdown.Item href="/student/viewEnquiries">View Enquiries</NavDropdown.Item>
              </>
            )}
            <NavDropdown.Divider />
            <NavDropdown.Item href="#action/3.4">
              Contact Us
            </NavDropdown.Item>
          </NavDropdown>
        </Nav>
        {loggedInStudent ? (
          <Navbar.Text style={{ paddingRight: "20px" }}>
            Welcome, <a href="/student/profile">{loggedInStudent.firstName}{" "}{loggedInStudent.lastName}</a>{"!"}
            <Button className='logoutButton' onClick={handleLogout}>Logout</Button>
          </Navbar.Text>
        ) : (
          <Navbar.Text style={{ paddingRight: "20px" }}>
            Not signed in: <a href="/student/login">Sign in</a>
          </Navbar.Text>
        )}
      </Navbar.Collapse >
    </Navbar>
  );
}

export default NavbarComp;
