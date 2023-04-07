import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';

import Header from '../../../components/dashboard/Header';
import Menu from '../../../components/dashboard/Menu';
import Footer from '../../../components/dashboard/Footer';

import API from '../../../../util/API';
import apiPaths from '../../../../util/apiPaths';
import { EncodedTextConverter } from '../../../../util/encodedTextConverter';

const PartnerUniversityModule = () => {
    const { puName } = useParams();
    const [name, setName] = useState("");
    const [code, setCode] = useState("");
    const [description, setDescription] = useState("");

    const handleCreate = async () => {
        try {
            const createdPUModule = {
                code,
                name,
                description,
            };

            const apiPath = `${apiPaths.listOfModules}/createModuleForPU?puName=${EncodedTextConverter.convertToEncodedTextForUrl(puName)}`;
            await API.post(apiPath, createdPUModule);

            alert("PU has been created successfully");
        } catch (error) {
            console.error(error);
        }
    };

    return (
        <div>
            <Header />
            <Menu />
            <div className="content-wrapper">
                <div className="card">
                    <div className="card card-primary">
                        <div className="card-header">
                            <h3 className="card-title">Create Partner University Module</h3>
                        </div>
                        <div className="card-body">
                            <div className="form-group">
                                <label htmlFor="inputName">Code</label>
                                <input type="text" id="inputName" className="form-control" placeholder="Input a module code" onChange={(e) => setCode(e.target.value)} />
                            </div>
                            <div className="form-group">
                                <label htmlFor="inputName">Name</label>
                                <input type="text" id="inputName" className="form-control" placeholder="Input a module name" onChange={(e) => setName(e.target.value)} />
                            </div>
                            <div className="form-group">
                                <label htmlFor="inputDescription">Description</label>
                                <textarea id="inputDescription" className="form-control" rows={4} placeholder="Input a description" onChange={(e) => setDescription(e.target.value)} />
                            </div>
                            <div className="text-center">
                                <button className="btn btn-success mr-2" onClick={handleCreate}>Create</button>
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

export default PartnerUniversityModule;