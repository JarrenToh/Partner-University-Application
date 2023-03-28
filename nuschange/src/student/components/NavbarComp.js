import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import React, { useState, useEffect } from "react";
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';
import logo from './NUSChange-logoV3.png';
import './Navbar.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import UniversityRankings from '../UniversityRankings';

function BasicExample() {
  const API_URL = "http://localhost:8080/PU-war/webresources/pu";
  const [pus, setPUs] = useState([]);

  useEffect(() => {
    searchPUs("");
  }, []);

  const searchPUs = async (title) => {
    const response = await fetch(`${API_URL}`);
    const data = await response.json();
    console.log(data);
    setPUs(data);
  };

  return (
    <Navbar bg="light" variant="light">
      
      <Navbar.Brand href="/student" className='navBrand'>
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
            <Nav.Link href="/student" className='navLink'>Home</Nav.Link>
            <Nav.Link href="/student/university-rankings?search=" className='navLink'>Ranking</Nav.Link>
            <NavDropdown title="Support" id="basic-nav-dropdown"className='navLinkDropDown' >
              <NavDropdown.Item href="#action/3.1">FAQs</NavDropdown.Item>
              <NavDropdown.Item href="#action/3.2">
                Submit a Ticket
              </NavDropdown.Item>
              <NavDropdown.Item href="#action/3.3">Submit a Question</NavDropdown.Item>
              <NavDropdown.Divider />
              <NavDropdown.Item href="#action/3.4">
                Contact Us
              </NavDropdown.Item>
            </NavDropdown>
          </Nav>
        </Navbar.Collapse>
      
    </Navbar>
  );
}

export default BasicExample;