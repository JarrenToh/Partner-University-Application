import React, { useState, useEffect, useContext } from "react";
import { useLocation, useNavigate } from "react-router-dom";

import Footer from "../../../components/dashboard/Footer";
import Header from "../../../components/dashboard/Header";
import Menu from "../../../components/dashboard/Menu";

import API from "../../../../util/API";
import apiPaths from "../../../../util/apiPaths";
import { DateTimeConverter } from "../../../../util/dateTimeConverter";
import { userSupportAdminPaths } from "../../../../util/adminRoutes";
import { AuthContext } from "../../../../AuthContext";

const Enquiry = () => {

    const [data, setData] = useState([]);
    const [adminId, setAdminId] = useState(undefined);
    const navigate = useNavigate();
    const location = useLocation();

    const { loggedInAdmin } = useContext(AuthContext);
    
    const handleButtonClick = (enquiryId) => {
        navigate(`/${userSupportAdminPaths.viewEnquiries}/${enquiryId}`);
    };

    useEffect(() => {
        if (loggedInAdmin === null) return;

        const fetchData = async () => {
            try {

                const { pathname } = location;
                const pathSegments = pathname.split('/');
                const isLastWordAssigned = pathSegments[pathSegments.length - 1] === 'assigned';
                
                let path = "";

                if (!isLastWordAssigned) {
                    path = apiPaths.listOfEnquiries;
                } else {
                    console.log(loggedInAdmin.adminId);
                    path = `${apiPaths.listOfEnquiries}/assigned?adminId=${loggedInAdmin.adminId}`;
                    setAdminId(loggedInAdmin.adminId);
                }

                const response = await API.get(path);
                setData(response.data);
            } catch (error) {
                console.error(error);
            }
        };
        fetchData();
    }, [loggedInAdmin]);

    return (
        <div>
            <Header />
            <Menu />
            <div className="content-wrapper">
                <div className="card">
                    <div className="card-header">
                        <h3 className="card-title">{adminId === undefined ? "Student Enquiries" : "Past Responses to Student Enquiries"}</h3>
                    </div>
                    <div className="card-body">
                        <table id="example1" className="table table-bordered table-striped">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Title</th>
                                    <th>Content</th>
                                    <th>Status</th>
                                    <th>Created At</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                {(data.filter(item => item.status === "PENDING").length > 0 || (data.filter(item => item.status !== "PENDING").length > 0 && adminId !== undefined)) ? (
                                    data.map((item, index) => (item.status === "PENDING" || (item.status !== "PENDING" && adminId !== undefined)) && (
                                        <tr key={index}>
                                            <td>{item.enquiryId}</td>
                                            <td>{item.title}</td>
                                            <td>{item.content}</td>
                                            <td>{item.status}</td>
                                            <td>{DateTimeConverter.convertDateForNicerOutput(item.enquiryDate)}</td>
                                            <td>
                                                <button onClick={() => handleButtonClick(item.enquiryId)} type="button" className="btn btn-primary">View Details</button>
                                            </td>
                                        </tr>
                                    )
                                    ))
                                    : (
                                        <tr>
                                            <td colSpan={7} style={{ textAlign: "center" }}>No data available</td>
                                        </tr>
                                    )}
                            </tbody>
                            <tfoot>
                                <tr>
                                    <th>ID</th>
                                    <th>Title</th>
                                    <th>Content</th>
                                    <th>Status</th>
                                    <th>Created At</th>
                                    <th>Actions</th>
                                </tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
            <Footer />
        </div>
    )
};

export default Enquiry;