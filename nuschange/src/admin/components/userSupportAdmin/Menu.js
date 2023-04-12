import React from 'react';

import { userSupoortAdminPaths } from '../../../util/adminRoutes';

const Menu = () => {
    return (
        <ul className="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">
            <li className="nav-item">
                <button className="nav-link">
                    <i className="nav-icon fas fa-question-circle" />
                    <p>
                        Frequently-Asked-Questions (FAQ)
                        <i className="right fas fa-angle-left" />
                    </p>
                </button>
                <ul className="nav nav-treeview">
                    <li className="nav-item">
                        <a href={userSupoortAdminPaths.createFaq} className="nav-link">
                            <i className="far fa-circle nav-icon" />
                            <p>Create</p>
                        </a>
                    </li>
                    <li className="nav-item">
                        <a href={userSupoortAdminPaths.viewFaqs} className="nav-link">
                            <i className="far fa-circle nav-icon" />
                            <p>View</p>
                        </a>
                    </li>
                </ul>
            </li>
            <li className="nav-item">
                <button className="nav-link">
                    <i className="nav-icon fas fa-question-circle" />
                    <p>
                        Student Enquiries
                        <i className="right fas fa-angle-left" />
                    </p>
                </button>
                <ul className="nav nav-treeview">
                    <li className="nav-item">
                        <a href={userSupoortAdminPaths.viewEnquiries} className="nav-link">
                            <i className="far fa-circle nav-icon" />
                            <p>View</p>
                        </a>
                    </li>
                    <li className="nav-item">
                        <a href={userSupoortAdminPaths.viewAssignedEnquiries} className="nav-link">
                            <i className="far fa-circle nav-icon" />
                            <p>View Past Responses</p>
                        </a>
                    </li>
                </ul>
            </li>
        </ul>
    )
};

export default Menu;