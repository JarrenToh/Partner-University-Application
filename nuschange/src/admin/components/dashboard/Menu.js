import React, { useContext } from 'react';

import UserSupportAdminMenu from '../userSupportAdmin/Menu';
import SystemSupportAdminMenu from '../systemSupportAdmin/Menu';

import { AuthContext } from '../../../AuthContext';
import logo from "../../../NUSChange-logoV3.png";

const Menu = () => {

    const { loggedInAdmin } = useContext(AuthContext);
    const userGroupEnum = loggedInAdmin !== null && loggedInAdmin.userGroupEnum;

    return (
        <div>
            <aside className="main-sidebar elevation-4" style={{ background: 'white' }}>
                <img src={logo} alt="NUSChange Logo" style={{ 'width': '100%' }} />
                <br />
                <div className="sidebar">
                    <nav className="mt-2">
                        {userGroupEnum === "USER_SUPPORT" ? <UserSupportAdminMenu/> : <SystemSupportAdminMenu/>}
                    </nav>
                </div>
            </aside>
        </div>
    )
};

export default Menu;