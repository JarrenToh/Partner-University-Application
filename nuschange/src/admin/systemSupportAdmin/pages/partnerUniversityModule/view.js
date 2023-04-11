import React, { useState, useEffect } from "react";
import { useParams, Navigate, useNavigate } from "react-router-dom";

import Header from "../../../components/dashboard/Header";
import Menu from "../../../components/dashboard/Menu";
import Footer from "../../../components/dashboard/Footer";

import API from "../../../../util/API";
import apiPaths from "../../../../util/apiPaths";

import { convertToEncodedTextForUrl, convertNameToSlug } from "../../../../util/urlTextConverter";

const PUModuleDetails = () => {
    const navigate = useNavigate();

    const { puName, puModuleCode } = useParams();
    const [id, setId] = useState(-1);
    const [name, setName] = useState("");
    const [code, setCode] = useState("");
    const [description, setDescription] = useState("");

    const handleEdit = async (id) => {
        try {
            const updatedPUModule = {
                name,
                code,
                description,
            };
            const redirectedUrl = `${`/partnerUniversities/${convertNameToSlug(puName)}/modules`}`;

            const apiPath = `${apiPaths.listOfPUModules}/${id}`;
            await API.put(apiPath, updatedPUModule);

            alert("PU Module has been updated successfully");
            navigate(redirectedUrl);
        } catch (error) {
            console.error(error);
        }
    };

    const handleDelete = async () => {
        try {
            const apiPath = `${apiPaths.listOfFaqs}/${id}`;
            await API.delete(apiPath);
            alert("PU Module has been deleted successfully!");
            // TODO: Redirect to FAQs list page
        } catch (error) {
            console.error(error);
        }
    };

    useEffect(() => {
        const fetchData = async () => {
            try {
                console.log("puName " + puName);
                console.log("puModuleCode " + puModuleCode);

                const apiPath = `${apiPaths.listOfPUModules}/searchPUModuleByCodeAndPUName?code=${puModuleCode}&name=${convertToEncodedTextForUrl(puName)}`
                const response = await API.get(apiPath);
                const data = response.data;

                const id = data.moduleId;
                const name = data.name;
                const code = data.code;
                const description = data.description;

                setId(id);
                setName(name);
                setCode(code);
                setDescription(description);
            } catch (error) {
                console.error(error);
            }
        };
        
        fetchData();
    }, [id]);

    return (
        <div>
            <Header />
            <Menu />
            <div className="content-wrapper">
                <div className="card">
                    <div className="card card-primary">
                        <div className="card-header">
                            <h3 className="card-title">View PU Module Details</h3>
                        </div>
                        <div className="card-body">
                            <div className="form-group">
                                <label htmlFor="inputName">Name</label>
                                <input type="text" id="inputName" className="form-control" value={name} onChange={(e) => setName(e.target.value)} />
                            </div>
                            <div className="form-group">
                                <label htmlFor="inputName">Code</label>
                                <input type="text" id="inputCode" className="form-control" value={code} onChange={(e) => setCode(e.target.value)} />
                            </div>
                            <div className="form-group">
                                <label htmlFor="inputDescription">Description</label>
                                <textarea id="inputDescription" className="form-control" rows={4} value={description} onChange={(e) => setDescription(e.target.value)} />
                            </div>
                            <div className="text-center">
                                <button className="btn btn-success mr-2" onClick={() => handleEdit(id)}>Save Changes</button>
                                <button className="btn btn-danger" onClick={handleDelete}>Delete</button>
                            </div>
                        </div>
                        <br />
                    </div>
                </div>
            </div>
            <Footer />
        </div>
    );
};

export default PUModuleDetails;
