import React, { useContext } from "react";
import Navbar from 'react-bootstrap/Navbar';
import { AuthContext } from '../login/AuthContext';
import { Card, CardBody, CardHeader } from 'reactstrap';
import NotLoggedIn from '../components/NotLoggedInPage';

const NotLoggedInPage = () => {
    return (
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
                    <h3 className="card-title" style={{fontSize : '25px', color : 'black'}}>You are not logged in</h3>
                </CardHeader>
                <CardBody
                    style={{
                        textAlign: `webkit-center`
                    }}
                >
                    <br/>
                    <p style={{fontSize : '20px'}}>Please <a href="/student/login">sign in</a> to access this page.</p>
                    <br/>
                </CardBody>
            </Card>
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
