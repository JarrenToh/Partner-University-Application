import React, { useState, useEffect } from "react";
import { useNavigate } from 'react-router-dom';

import Header from "../../../components/dashboard/Header";
import Menu from "../../../components/dashboard/Menu";
import Footer from "../../../components/dashboard/Footer";

import API from "../../../../util/API";
import apiPaths from "../../../../util/apiPaths";

const PartnerUuniversity = () => {

    const [data, setData] = useState([]);
    const navigate = useNavigate();

    const handleViewDetailsButtonClick = (puId) => {
        navigate(`/partnerUniversities/${puId}`);
    };

    const handleViewModulesButtonClick = (puId) => {
        navigate(`/partnerUniversities/${puId}`);
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
    }, []);

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
                                        <td style={{ display: 'flex' }}>
                                            <div>
                                                <button onClick={() => handleViewDetailsButtonClick(item.puId)} type="button" className="btn btn-primary">View Details</button>
                                            </div>
                                            <div>
                                                <button onClick={() => handleViewModulesButtonClick(item.name)} type="button" className="btn btn-primary ml-2">View Modules</button>
                                            </div>
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
