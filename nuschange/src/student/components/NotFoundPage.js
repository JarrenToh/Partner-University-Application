import React, { useContext, useState } from "react";
import Navbar from 'react-bootstrap/Navbar';
// import { AuthContext } from '../login/AuthContext';
import { Card, CardBody, CardHeader } from 'reactstrap';
import NotLoggedIn from './NotFoundPage';
import { AuthContext } from '../../AuthContext';
import NavbarCompo from './NavbarComp';
import { FaTimesCircle } from 'react-icons/fa';

const NotLoggedInPage = () => {
    const { loggedInStudent } = useContext(AuthContext);
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [user, setUser] = useState(null);

    return (
        <div className="wrapper">
            <NavbarCompo isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn} user={user} />
            <div
                className="form-container"
                style={{
                    ...NotLoggedIn.formContainerStyle,
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
                        ...NotLoggedIn.formStyle,
                        backgroundColor: 'rgba(255,255,255,0.7)',
                        boxShadow: '0 0 10px rgba(0,0,0,0.3)',
                        minWidth: '800px', // Set the maximum width of the card to 400px
                        borderRadius: '2%'
                    }}
                >
                    <CardHeader>
                        <h3 className="card-title" style={{ fontSize: '25px', color: 'black', padding: '10px', display: 'flex', alignItems: 'center' }}>
                            <span style={{ paddingRight: '10px' }}><FaTimesCircle /></span>
                            404 Error: Page Not Found
                        </h3>
                    </CardHeader>
                    <CardBody
                        style={{
                            textAlign: `webkit-center`
                        }}
                    >
                        <br />
                        <p style={{ fontSize: '20px' }}>Oops, it looks like the page you're looking for doesn't exist.</p>
                        <p style={{ fontSize: '20px' }}>This could be because the URL is incorrect or the page has been moved or deleted.</p>
                        <p style={{ fontSize: '20px' }}>We apologize for any inconvenience.</p>
                        <br />
                    </CardBody>
                </Card>
            </div>
        </div>
    );
};

const NavbarComp = () => {
    const { loggedInStudent, logout } = useContext(AuthContext);

    const handleLogout = () => {
        logout();
    };

    return (
        <>
            {loggedInStudent ? (
                <Navbar bg="light" variant="light">
                    {/* Existing Navbar code */}
                </Navbar>
            ) : (
                <NotLoggedInPage />
            )}
        </>
    );
};

export default NavbarComp;
