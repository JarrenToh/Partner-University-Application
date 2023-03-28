import React from 'react';

import Header from '../admin/components/dashboard/Header';
import Menu from '../admin/components/dashboard/Menu';
import Dashboard from "../admin/components/dashboard/Dashboard";
import Footer from "../admin/components/dashboard/Footer";

const Main = () => {
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