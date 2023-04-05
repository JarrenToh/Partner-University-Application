import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { useNavigate } from 'react-router-dom';

import Header from "../../../components/dashboard/Header";
import Menu from "../../../components/dashboard/Menu";
import Footer from "../../../components/dashboard/Footer";

import API from "../../../../util/API";
import apiPaths from "../../../../util/apiPaths";

const PartnerUuniversityModules = () => {
    const { puName } = useParams();
    const [data, setData] = useState([]);
    const navigate = useNavigate();

    const handleButtonClick = (puId) => {
        navigate(`/partnerUniversities/getPUByName/${puName}`);
    };

    useEffect(() => {
        const fetchData = async () => {
            try {
                const formattedPUName = puName.split("-").join(" ").trim();
                const encodedPUName = encodeURIComponent(formattedPUName);

                const apiPath = `${apiPaths.listOfPUs}/getPUByName/${encodedPUName}`;

                const response = await API.get(apiPath);
                console.log("response is " + response);

                setData(response.data);

                console.log("length of data " + response.length);
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
                    </div>
                    <div className="card-body">
                        <table id="example1" className="table table-bordered table-striped">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Module Code</th>
                                    <th>Description</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                {console.log("data is " + data.length)}
                                {/* {data.map((item) => (
                                    <tr key={item.puId}>
                                        <td>{item.moduleId}</td>
                                        <td>{item.code}</td>
                                        <td>{item.description}</td>
                                        <td>
                                            <button onClick={() => handleButtonClick(item.puId)} type="button" className="btn btn-primary">View Details</button>
                                        </td>
                                    </tr>
                                ))} */}
                            </tbody>
                            <tfoot>
                                <tr>
                                    <th>ID</th>
                                    <th>Module Code</th>
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

export default PartnerUuniversityModules;
