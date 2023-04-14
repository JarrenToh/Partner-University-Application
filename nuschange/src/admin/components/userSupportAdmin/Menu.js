import React from 'react';

import { userSupportAdminPaths } from '../../../util/adminRoutes';

const Menu = () => {
    return (
        <ul className="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">
            <li className="nav-item">
                <button className="nav-link">
                    <i className="nav-icon fas fa-question-circle" />
                    <p>
                        FAQ
                        <i className="right fas fa-angle-left" />
                    </p>
                </button>
                <ul className="nav nav-treeview">
                    <li className="nav-item">
                        <a href={`/admin${userSupportAdminPaths.createFaq}`} className="nav-link">
                            <i className="far fa-circle nav-icon" />
                            <p>Create</p>
                        </a>
                    </li>
                    <li className="nav-item">
                        <a href={`/admin${userSupportAdminPaths.viewFaqs}`} className="nav-link">
                            <i className="far fa-circle nav-icon" />
                            <p>View</p>
                        </a>
                    </li>
                </ul>
            </li>
            <li className="nav-item">
                <button className="nav-link">
                    <i className="nav-icon fas fa-user" />
                    <p>
                        Student Enquiry
                        <i className="right fas fa-angle-left" />
                    </p>
                </button>
                <ul className="nav nav-treeview">
                    <li className="nav-item">
                        <a href={`/admin${userSupportAdminPaths.viewEnquiries}`} className="nav-link">
                            <i className="far fa-circle nav-icon" />
                            <p>View</p>
                        </a>
                    </li>
                    <li className="nav-item">
                        <a href={`/admin${userSupportAdminPaths.viewAssignedEnquiries}`} className="nav-link">
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