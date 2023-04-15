import React, { useState, useEffect } from "react";
import { useNavigate } from 'react-router-dom';

import Header from "../../../components/dashboard/Header";
import Menu from "../../../components/dashboard/Menu";
import Footer from "../../../components/dashboard/Footer";

import API from "../../../../util/API";
import apiPaths from "../../../../util/apiPaths";

import { convertNameToSlug } from "../../../../util/urlTextConverter";
import { systemSupportAdminPaths } from "../../../../util/adminRoutes";

import { Helmet } from "react-helmet";

const PartnerUniversity = () => {

    const [data, setData] = useState([]);
    const navigate = useNavigate();

    const handleViewDetailsButtonClick = (puName) => {
        navigate(`/admin${systemSupportAdminPaths.viewPUs}/${convertNameToSlug(puName)}`);
    };

    const handleViewModulesButtonClick = (puName) => {
        navigate(`/admin${systemSupportAdminPaths.viewPUs}/${convertNameToSlug(puName)}/modules`);
    };

    const handleSort = (data) => {
        return data.sort((a, b) => a.puId - b.puId);
    }

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await API.get(apiPaths.listOfPUs);
                setData(handleSort(response.data));
            } catch (error) {
                console.error(error);
            }
        };
        fetchData();
    }, [data]);

    return (
        <div>
            <Helmet>
                <title>View PU</title>
            </Helmet>
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
                                {data.length > 0 ? (data.map((item) => (
                                    <tr key={item.puId}>
                                        <td>{item.puId}</td>
                                        <td>{item.name}</td>
                                        <td>{item.description}</td>
                                        <td>
                                            <div style={{ display: 'flex', backgroundColor: '#f8f9fa' }}>
                                                <div>
                                                    <button onClick={() => handleViewDetailsButtonClick(item.name)} type="button" className="btn btn-primary">View Details</button>
                                                </div>
                                                <div>
                                                    <button onClick={() => handleViewModulesButtonClick(item.name)} type="button" className="btn btn-primary ml-2">View Modules</button>
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                )
                                )) : (
                                    <tr>
                                        <td colSpan={4} style={{ textAlign: "center" }}>No data available</td>
                                    </tr>
                                )}
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

export default PartnerUniversity;
