import React, { useEffect } from 'react';
import { AuthProvider } from '../AuthContext';
import { useNavigate } from 'react-router-dom';

import Header from '../admin/components/dashboard/Header';
import Menu from '../admin/components/dashboard/Menu';
import Dashboard from "../admin/components/dashboard/Dashboard";
import Footer from "../admin/components/dashboard/Footer";

const Main = () => {
    const { loggedInAdmin } = AuthProvider;
    const navigate = useNavigate();

    useEffect(() => {
        if (localStorage.getItem("loggedInAdmin") === null) {
            navigate('../login');
        }
    }, [loggedInAdmin, navigate]);

    return (
        <div>
            <Header />
            <Menu />
            <Dashboard />
            <Footer />
        </div>
    )
};

export default Main;