import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

import Footer from "../../../components/dashboard/Footer";
import Header from "../../../components/dashboard/Header";
import Menu from "../../../components/dashboard/Menu";

import API from "../../../../util/API";
import apiPaths from "../../../../util/apiPaths";
import { DateTimeConverter } from "../../../../util/dateTimeConverter";

const Enquiry = ({ adminId }) => {

    const [data, setData] = useState([]);
    const navigate = useNavigate();

    const handleButtonClick = (enquiryId) => {
        navigate(`/enquiries/${enquiryId}`);
    };

    useEffect(() => {
        const fetchData = async () => {
            try {

                let path = "";

                if (adminId === undefined) {
                    path = apiPaths.listOfEnquiries;
                } else {
                    // TODO: change to adminId dynamically
                    path = `${apiPaths.listOfEnquiries}/assigned?adminId=${adminId}`;
                }

                const response = await API.get(path);
                setData(response.data);
            } catch (error) {
                console.error(error);
            }
        };
        fetchData();
    }, [adminId]);

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
                                    data.map((item, index) => item.status === "PENDING" || (item.status !== "PENDING" && adminId !== undefined) && (
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