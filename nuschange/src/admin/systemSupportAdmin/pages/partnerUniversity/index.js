import React, { useState, useEffect } from "react";
import { useNavigate } from 'react-router-dom';

import Header from "../../../components/dashboard/Header";
import Menu from "../../../components/dashboard/Menu";
import Footer from "../../../components/dashboard/Footer";

import API from "../../../../util/API";
import apiPaths from "../../../../util/apiPaths";
import { DateTimeConverter } from "../../../../util/dateTimeConverter";

const PartnerUuniversity = () => {

    const [data, setData] = useState([]);
    const [showAll, setShowAll] = useState(true);
    const navigate = useNavigate();

    const handleButtonClick = (faqId) => {
        navigate(`/faqs/${faqId}`);
    };

    useEffect(() => {
        const fetchData = async () => {
            try {
                console.log("path " + apiPaths.listOfPUs)
                const response = await API.get(apiPaths.listOfPUs);
                console.log("data " + response.data);
                if (showAll) {
                    setData(response.data);
                } else {
                    // TODO: need to change this dyanmically later
                    const filteredData = response.data.filter(item => item.createdBy.adminId === 1); // assume you have the current user's id stored somewhere
                    setData(filteredData);
                }
            } catch (error) {
                console.error(error);
            }
        };
        fetchData();
    }, [showAll]);

    return (
        <div>
            <Header />
            <Menu />
            <div className="content-wrapper">
                <div className="card">
                    <div className="card-header">
                        <h3 className="card-title">Partner Universities</h3>
                    </div>
                    <div className="card-body">
                        <table id="example1" className="table table-bordered table-striped">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Name</th>
                                    <th>Description</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                {data.map((item) => (
                                    <tr key={item.puId}>
                                        <td>{item.puId}</td>
                                        <td>{item.name}</td>
                                        <td>{item.description}</td>
                                        <td>
                                            <button onClick={() => handleButtonClick(item.faqId)} type="button" className="btn btn-primary">View Details</button>
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                            <tfoot>
                                <tr>
                                    <th>ID</th>
                                    <th>Name</th>
                                    <th>Description</th>
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

export default PartnerUuniversity;
