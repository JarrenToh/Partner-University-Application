import React from "react";

import Header from "./components/dashboard/Header";
import Menu from "./components/dashboard/Menu";
import Footer from "./components/dashboard/Footer";

const NotFound = () => {
    return (
        <div>
            <Header />
            <Menu />
            <div>

            </div>
            <div className="content-wrapper">
                <div className="card">
                    <div className="card card-primary">
                        <div className="card-header">
                            <h3 className="card-title">Not Found</h3>
                        </div>
                        <div className="card-body">
                            <div className="form-group">
                                <h1>404: Page Not Found</h1>
                                <p>Sorry, the page you were looking for could not be found.</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <Footer />
        </div>
    );
};

export default NotFound;