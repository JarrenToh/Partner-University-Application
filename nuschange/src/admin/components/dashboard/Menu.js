import React from 'react';

import UserSupportAdminMenu from '../userSupportAdmin/Menu';
import SystemSupportAdminMenu from '../systemSupportAdmin/Menu';


import logo from "../../../NUSChange-logoV3.png";

const Menu = () => {

    // const admin = "UserSupportAdmin";
    const admin = "SystemSupportAdmin";

    return (
        <div>
            <aside className="main-sidebar elevation-4" style={{ background: 'white' }}>
                <img src={logo} alt="NUSChange Logo" style={{ 'width': '100%' }} />
                <br />
                <div className="sidebar">
                    <nav className="mt-2">
                        {admin === "UserSupportAdmin" ? <UserSupportAdminMenu/> : <SystemSupportAdminMenu/>}
                    </nav>
                </div>
            </aside>
        </div>
    )
};

export default Menu;