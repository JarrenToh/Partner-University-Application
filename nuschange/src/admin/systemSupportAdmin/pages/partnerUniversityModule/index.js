import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { useNavigate } from 'react-router-dom';

import Header from "../../../components/dashboard/Header";
import Menu from "../../../components/dashboard/Menu";
import Footer from "../../../components/dashboard/Footer";

import API from "../../../../util/API";
import apiPaths from "../../../../util/apiPaths";
import { convertToEncodedTextForUrl } from "../../../../util/urlTextConverter";

const PartnerUuniversityModules = () => {
    const { puName } = useParams();
    const [modules, setModules] = useState([]);
    const navigate = useNavigate();

    const handleButtonClick = (puCode) => {
        navigate(`/partnerUniversities/${puName}/modules/${puCode}`);
    };

    const handleCreatePUModuleButtonClick = () => {
        navigate(`/partnerUniversities/${puName}/modules/create`);
    };

    useEffect(() => {
        const fetchData = async () => {
            try {
                const encodedPUName = convertToEncodedTextForUrl(puName);

                const apiPath = `${apiPaths.listOfPUs}/getPUByName/${encodedPUName}`;

                const response = await API.get(apiPath);

                setModules(response.data.modules);
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
                        <h3 className="card-title">Partner University Modules</h3>
                        <button type="button" className="btn btn-block btn-outline-dark" onClick={() => handleCreatePUModuleButtonClick()}>Create Partner University Module</button>
                    </div>
                    <div className="card-body">
                        <table id="example1" className="table table-bordered table-striped">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Module Code</th>
                                    <th>Module Name</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                {modules.length > 0 ? (modules.map((item) => (
                                    <tr key={item.moduleId}>
                                        <td>{item.moduleId}</td>
                                        <td>{item.code}</td>
                                        <td>{item.name}</td>
                                        <td>
                                            <button onClick={() => handleButtonClick(item.code)} type="button" className="btn btn-primary">View Details</button>
                                        </td>
                                    </tr>
                                )
                                ))
                                    : (
                                        <tr>
                                            <td colSpan={4} style={{ textAlign: "center" }}>No data available</td>
                                        </tr>
                                    )
                                }
                            </tbody>
                            <tfoot>
                                <tr>
                                    <th>ID</th>
                                    <th>Module Code</th>
                                    <th>Module Name</th>
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

export default PartnerUuniversityModules;
