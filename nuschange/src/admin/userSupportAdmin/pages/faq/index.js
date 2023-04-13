import React, { useState, useEffect, useContext } from "react";
import { useNavigate } from 'react-router-dom';

import Header from "../../../components/dashboard/Header";
import Menu from "../../../components/dashboard/Menu";
import Footer from "../../../components/dashboard/Footer";

import API from "../../../../util/API";
import apiPaths from "../../../../util/apiPaths";
import { DateTimeConverter } from "../../../../util/dateTimeConverter";
import { userSupportAdminPaths } from "../../../../util/adminRoutes";
import { AuthContext } from "../../../../AuthContext";

const FAQ = () => {

    const [data, setData] = useState([]);
    const [showAll, setShowAll] = useState(true);
    const navigate = useNavigate();

    const { loggedInAdmin } = useContext(AuthContext);

    const handleButtonClick = (faqId) => {
        navigate(`/${userSupportAdminPaths.viewFaqs}/${faqId}`);
    };

    useEffect(() => {
        if (loggedInAdmin === null) return;

        const fetchData = async () => {
            try {
                const response = await API.get(apiPaths.listOfFaqs);
                if (showAll) {
                    setData(response.data);
                } else {
                    const filteredData = response.data.filter(item => item.createdBy.adminId === loggedInAdmin.adminId); // assume you have the current user's id stored somewhere
                    setData(filteredData);
                }
            } catch (error) {
                console.error(error);
            }
        };
        fetchData();
    }, [showAll, loggedInAdmin]);

    const handleToggle = () => {
        setShowAll(!showAll);
    };

    return (
        <div>
            <Header />
            <Menu />
            <div className="content-wrapper">
                <div className="card">
                    <div className="card-header">
                        <h3 className="card-title">Frequently-Asked-Questions (FAQs)</h3>
                        <button type="button" className="btn btn-block btn-outline-dark" onClick={handleToggle}>{showAll ? 'Show only my FAQs' : 'Show all FAQs'}</button>
                    </div>
                    <div className="card-body">
                        <table id="example1" className="table table-bordered table-striped">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Question</th>
                                    <th>Answer</th>
                                    <th>Created By</th>
                                    <th>Created At</th>
                                    <th>Last Edit At</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                {data.length > 0 ? (
                                    data.map((item, index) => (
                                        <tr key={index}>
                                            <td>{item.faqId}</td>
                                            <td>{item.question}</td>
                                            <td>{item.answer}</td>
                                            <td>{item.createdBy.name}</td>
                                            <td>{DateTimeConverter.convertDateForNicerOutput(item.created)}</td>
                                            <td>{item.lastEdit === undefined ? "-" : DateTimeConverter.convertDateForNicerOutput(item.lastEdit)}</td>
                                            <td>
                                                <button onClick={() => handleButtonClick(item.faqId)} type="button" className="btn btn-primary">View Details</button>
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
                                    <th>Question</th>
                                    <th>Answer</th>
                                    <th>Created By</th>
                                    <th>Created At</th>
                                    <th>Last Edit At</th>
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

export default FAQ;
