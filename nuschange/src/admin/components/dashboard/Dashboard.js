import React from 'react';
import Chart from "chart.js/auto";
import { CategoryScale } from "chart.js";

import LikedPUs from '../systemSupportAdmin/chart/LikedPUs';
import InappropriatenessContent from '../systemSupportAdmin/chart/InappropriatenessContent';
import StudentEnquiries from '../userSupportAdmin/chart/StudentEnquiries';

const Dashboard = () => {

    // const user = "SystemSupportAdmin";
    const user = "UserSupportAdmin";
    Chart.register(CategoryScale);

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
                            {user === "SystemSupportAdmin" &&
                                (
                                    <div>
                                        <LikedPUs />
                                        <InappropriatenessContent />
                                    </div>
                                )
                            }
                            {user === "UserSupportAdmin" &&
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
