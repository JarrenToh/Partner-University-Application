import React from 'react';

import Header from '../admin/components/dashboard/Header';
import Menu from '../admin/components/dashboard/Menu';
import Dashboard from "../admin/components/dashboard/Dashboard";
import Footer from "../admin/components/dashboard/Footer";

import { Helmet } from 'react-helmet';

const Main = () => {
    return (
        <div>
            <Helmet>
                <title>Home</title>
            </Helmet>
            <Header />
            <Menu />
            <Dashboard />
            <Footer />
        </div>
    )
};

export default Main;