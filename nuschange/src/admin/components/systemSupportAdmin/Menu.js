import React from 'react';

const Menu = () => {
    return (
        <ul className="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">
            <li className="nav-item">
                <a href="#" className="nav-link">
                    <i className="nav-icon fas fa-question-circle" />
                    <p>
                        Partner Universities
                        <i className="right fas fa-angle-left" />
                    </p>
                </a>
                <ul className="nav nav-treeview">
                    <li className="nav-item">
                        <a href="pages/charts/chartjs.html" className="nav-link">
                            <i className="far fa-circle nav-icon" />
                            <p>Create Partner University</p>
                        </a>
                    </li>
                    <li className="nav-item">
                        <a href="pages/charts/flot.html" className="nav-link">
                            <i className="far fa-circle nav-icon" />
                            <p>View Partner Universities</p>
                        </a>
                    </li>
                </ul>
            </li>
            <li className="nav-item">
                <a href="#" className="nav-link">
                    <i className="nav-icon fas fa-question-circle" />
                    <p>
                        Partner University Modules
                        <i className="right fas fa-angle-left" />
                    </p>
                </a>
                <ul className="nav nav-treeview">
                    <li className="nav-item">
                        <a href="pages/charts/chartjs.html" className="nav-link">
                            <i className="far fa-circle nav-icon" />
                            <p>Create Partner University Module</p>
                        </a>
                    </li>
                    <li className="nav-item">
                        <a href="pages/charts/flot.html" className="nav-link">
                            <i className="far fa-circle nav-icon" />
                            <p>View Partner University Modules</p>
                        </a>
                    </li>
                </ul>
            </li>
            <li className="nav-item">
                <a href="#" className="nav-link">
                    <i className="nav-icon fas fa-question-circle" />
                    <p>
                        Inappropriate of Reviews/Forum Posts
                        <i className="right fas fa-angle-left" />
                    </p>
                </a>
                <ul className="nav nav-treeview">
                    <li className="nav-item">
                        <a href="pages/charts/chartjs.html" className="nav-link">
                            <i className="far fa-circle nav-icon" />
                            <p>View Inappropriateness of Review/Forum Posts</p>
                        </a>
                    </li>
                    <li className="nav-item">
                        <a href="pages/charts/flot.html" className="nav-link">
                            <i className="far fa-circle nav-icon" />
                            <p>View Past Responses to Innapropriateness of Review/Forum Posts</p>
                        </a>
                    </li>
                </ul>
            </li>
        </ul>
    )
};

export default Menu;