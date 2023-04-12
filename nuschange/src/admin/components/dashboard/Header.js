import React, { Component } from 'react';
import path from "../../../util/reactPaths"

export default class Header extends Component {
    render() {
        return (
            <div>
                <nav className="main-header navbar navbar-expand navbar-white navbar-light">
                    <ul className="navbar-nav">
                        <li className="nav-item">
                            <button className="nav-link" style={{background: "none"}} data-widget="pushmenu"><i className="fas fa-bars" /></button>
                        </li>
                        <li className="nav-item d-none d-sm-inline-block">
                            <a href={path.main} className="nav-link">Home</a>
                        </li>
                        <li className="nav-item d-none d-sm-inline-block">
                            <a href="#" className="nav-link">Profile</a>
                        </li>
                    </ul>
                    <ul className="navbar-nav ml-auto">
                        <li className="nav-item d-none d-sm-inline-block">
                            <a href="#" className="nav-link">Logout</a>
                        </li>
                    </ul>
                </nav>
            </div>

        )
    }
}