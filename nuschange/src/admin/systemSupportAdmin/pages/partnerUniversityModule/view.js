import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { useNavigate } from 'react-router-dom';

import Header from "../../../components/dashboard/Header";
import Menu from "../../../components/dashboard/Menu";
import Footer from "../../../components/dashboard/Footer";

import API from "../../../../util/API";
import apiPaths from "../../../../util/apiPaths";
import { EncodedTextConverter } from "../../../../util/encodedTextConverter";

const PartnerUuniversityModuleDetails = () => {
    const { puName, puModuleCode } = useParams();

    const [id, setId] = useState(-1);
    const [name, setName] = useState("");
    const [code, setCode] = useState("");
    const [description, setDescription] = useState("");

    const navigate = useNavigate();

    // const handleButtonClick = (puId) => {
    //     navigate(`/partnerUniversities/getPUByName/${puName}`);
    // };

    const handleEdit = async () => {
        try {
            const updatedModule = {
                name,
                code,
                description
            };

            const apiPath = `${apiPaths.listOfModules}/${id}`;
            await API.put(apiPath, updatedModule);

            alert("Module has been updated successfully");
        } catch (error) {
            console.error(error);
        }
    };

    const handleDelete = async () => {
        try {
            const apiPath = `${apiPaths.listOfModules}/deletePUModuleFromPU/${id}?pu=${EncodedTextConverter.convertToEncodedTextForUrl(puName)}`;
            await API.delete(apiPath);
            alert("Module has been deleted successfully!");
            // TODO: Redirect to FAQs list page
        } catch (error) {
            console.error(error);
        }
    };

    useEffect(() => {
        const fetchData = async () => {
            try {
                const apiPath = `${apiPaths.listOfModules}/searchByModuleCode/?code=${puModuleCode}`;
                const response = await API.get(apiPath);
                const data = response.data;

                const moduleId = data.moduleId;
                const name = data.name;
                const code = data.code;
                const description = data.description;

                setId(moduleId);
                setName(name);
                setCode(code);
                setDescription(description);

            } catch (error) {
                console.error(error);
            }
        };
        fetchData();
    }, [puModuleCode]);

    return (
        <div>
            <Header />
            <Menu />
            <div className="content-wrapper">
                <div className="card">
                    <div className="card card-primary">
                        <div className="card-header">
                            <h3 className="card-title">View Module Details</h3>
                        </div>
                        <div className="card-body">
                            <div className="form-group">
                                <label htmlFor="inputCode">Code</label>
                                <input type="text" id="inputCode" className="form-control" value={code} onChange={(e) => setCode(e.target.value)} />
                            </div>
                            <div className="form-group">
                                <label htmlFor="inputName">Name</label>
                                <input type="text" id="inputName" className="form-control" value={name} onChange={(e) => setName(e.target.value)} />
                            </div>
                            <div className="form-group">
                                <label htmlFor="inputDescription">Description</label>
                                <textarea id="inputDescription" className="form-control" rows={4} value={description} onChange={(e) => setDescription(e.target.value)} />
                            </div>
                            <div className="text-center">
                                <button className="btn btn-success mr-2" onClick={handleEdit}>Save Changes</button>
                                <button className="btn btn-danger" onClick={handleDelete}>Delete</button>
                            </div>
                        </div>
                        <br />
                    </div>
                </div>
            </div>
            <Footer />
        </div>
    )
};

export default PartnerUuniversityModuleDetails;
