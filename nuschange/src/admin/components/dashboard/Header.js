import React, { useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from '../../../AuthContext';

const Header = () => {

    const navigate = useNavigate();
    const { loggedInAdmin, logoutAdmin } = useContext(AuthContext);

    const username = loggedInAdmin !== null && loggedInAdmin.username;
    const userGroupEnum = loggedInAdmin !== null && loggedInAdmin.userGroupEnum === "USER_SUPPORT" ? "userSupportAdmin" : "systemSupportAdmin";

    const handleLogout = () => {
        logoutAdmin();
        navigate('../login');
    };

    return (
        <div>
            <nav className="main-header navbar navbar-expand navbar-white navbar-light">
                <ul className="navbar-nav">
                    <li className="nav-item">
                        <button className="nav-link" style={{ background: "none" }} data-widget="pushmenu"><i className="fas fa-bars" /></button>
                    </li>
                    <li className="nav-item d-none d-sm-inline-block">
                        <a href={`/admin/${userGroupEnum}/main`} className="nav-link">Home</a>
                    </li>
                    <li className="nav-item d-none d-sm-inline-block">
                        <a href={`/admin/${userGroupEnum}/profile/${username}`} className="nav-link">Profile</a>
                    </li>
                </ul>
                <ul className="navbar-nav ml-auto">
                    <li className="nav-item d-none d-sm-inline-block">
                        <button className="nav-link btn btn-link" onClick={handleLogout}>Logout</button>
                    </li>
                </ul>
            </nav>
        </div>

    )
};

export default Header;
