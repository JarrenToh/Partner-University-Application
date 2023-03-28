import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';
import logo from './NUSChange-logoV3.png';
import './Navbar.css';
import 'bootstrap/dist/css/bootstrap.min.css';

function BasicExample() {
  return (
    <Navbar bg="light" variant="light">
      
      <Navbar.Brand href="#home" className='navBrand'>
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
            <Nav.Link href="/student/university-rankings" className='navLink'>Ranking</Nav.Link>
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