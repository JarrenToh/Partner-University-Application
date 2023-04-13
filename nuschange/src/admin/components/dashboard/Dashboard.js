import React, { useContext } from 'react';
import Chart from "chart.js/auto";
import { CategoryScale } from "chart.js";

import LikedPUs from '../systemSupportAdmin/chart/LikedPUs';
import InappropriatenessContent from '../systemSupportAdmin/chart/InappropriatenessContent';
import StudentEnquiries from '../userSupportAdmin/chart/StudentEnquiries';

import { AuthContext } from '../../../AuthContext';

const Dashboard = () => {

    Chart.register(CategoryScale);

    const { loggedInAdmin } = useContext(AuthContext);

    return (
        <div>
            <div className="content-wrapper">
                <div className="content-header">
                    <div className="container-fluid">
                        <div className="row mb-2">
                            <div className="col-sm-6">
                                <h1 className="m-0">Dashboard</h1>
                            </div>
                        </div>
                    </div>
                </div>
                <section className="content">
                    <div className="container-fluid">
                        <div className="row">
                            {loggedInAdmin !== null && loggedInAdmin.userGroupEnum === "SYSTEM_SUPPORT" &&
                                (
                                    <div>
                                        <LikedPUs />
                                        <InappropriatenessContent />
                                    </div>
                                )
                            }
                            {loggedInAdmin != null && loggedInAdmin.userGroupEnum === "USER_SUPPORT" &&
                                (
                                    <div>
                                        <StudentEnquiries/>
                                    </div>
                                )}
                        </div>
                    </div>
                </section>
            </div>
        </div>

    )
};

export default Dashboard;
