import React from 'react';
import { systemSupportAdminPaths } from '../../../util/adminRoutes';

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
                        <a href={systemSupportAdminPaths.createPu} className="nav-link">
                            <i className="far fa-circle nav-icon" />
                            <p>Create</p>
                        </a>
                    </li>
                    <li className="nav-item">
                        <a href={systemSupportAdminPaths.viewPUs} className="nav-link">
                            <i className="far fa-circle nav-icon" />
                            <p>View</p>
                        </a>
                    </li>
                </ul>
            </li>
            <li className="nav-item">
                <a href="#" className="nav-link">
                    <i className="nav-icon fas fa-question-circle" />
                    <p>
                        Inappropriate Content
                        <i className="right fas fa-angle-left" />
                    </p>
                </a>
                <ul className="nav nav-treeview">
                    <li className="nav-item">
                        <a href={systemSupportAdminPaths.viewInappropriateContent} className="nav-link">
                            <i className="far fa-circle nav-icon" />
                            <p>View</p>
                        </a>
                    </li>
                </ul>
            </li>
            <li className="nav-item">
                <a href="#" className="nav-link">
                    <i className="nav-icon fas fa-question-circle" />
                    <p>
                        Forum Topic
                        <i className="right fas fa-angle-left" />
                    </p>
                </a>
                <ul className="nav nav-treeview">
                    <li className="nav-item">
                        <a href={systemSupportAdminPaths.createForumTopic} className="nav-link">
                            <i className="far fa-circle nav-icon" />
                            <p>Create</p>
                        </a>
                    </li>
                    <li className="nav-item">
                        <a href={systemSupportAdminPaths.viewForumTopics} className="nav-link">
                            <i className="far fa-circle nav-icon" />
                            <p>View</p>
                        </a>
                    </li>
                </ul>
            </li>
        </ul>
    )
};

export default Menu;