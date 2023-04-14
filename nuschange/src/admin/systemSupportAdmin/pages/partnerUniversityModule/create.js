import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';

import Header from '../../../components/dashboard/Header';
import Menu from '../../../components/dashboard/Menu';
import Footer from '../../../components/dashboard/Footer';

import API from '../../../../util/API';
import apiPaths from '../../../../util/apiPaths';
import { convertToEncodedTextForUrl } from '../../../../util/urlTextConverter';

const PartnerUniversityModule = () => {
    const { puName } = useParams();
    const [name, setName] = useState("");
    const [code, setCode] = useState("");
    const [description, setDescription] = useState("");

    const [existingModules, setExistingModules] = useState([]);

    const [nameError, setNameError] = useState("");
    const [codeError, setCodeError] = useState("");
    const [descriptionError, setDescriptionError] = useState("");

    const [showModal, setShowModal] = useState(false);

    const handleCreate = async () => {
        if (validate()) {
            try {
                const createdPUModule = {
                    code,
                    name,
                    description,
                };

                const apiPath = `${apiPaths.listOfPUModules}/createModuleForPU?puName=${convertToEncodedTextForUrl(puName)}`;
                await API.post(apiPath, createdPUModule);

                setShowModal(true);
            } catch (error) {
                console.error(error);
            }
        }
    };

    const handleCancel = async () => {
        setName("");
        setCode("");
        setDescription("");
        setShowModal(false);
    };

    const validate = () => {
        let isValid = true;
        if (name.trim() === "") {
            setNameError("Please enter a name");
            isValid = false;
        } else {
            const duplicateName = existingModules.some(
                (module) => module.name.toLowerCase() === name.toLowerCase()
            );

            if (duplicateName) {
                setNameError("Name already exists");
                isValid = false;
            } else {
                setNameError("");
            }
        }
        if (code.trim() === "") {
            setCodeError("Please enter a code");
            isValid = false;
        } else {
            const duplicateCode = existingModules.some(
                (module) => module.code.toLowerCase() === code.toLowerCase()
            );

            if (duplicateCode) {
                setCodeError("Code already exists");
                isValid = false;
            } else {
                setCodeError("");
            }
        }
        if (description.trim() === "") {
            setDescriptionError("Please enter a description");
            isValid = false;
        } else {
            setDescriptionError("");
        }
        return isValid;
    };

    useEffect(() => {
        const fetchData = async () => {
            try {
                const encodedPUName = convertToEncodedTextForUrl(puName);

                const apiPath = `${apiPaths.listOfPUs}/getPUByName/${encodedPUName}`;

                const response = await API.get(apiPath);

                setExistingModules(response.data.modules);
            } catch (error) {
                console.error(error);
            }
        };

        fetchData();
    }, [puName])

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
                                <input type="text" id="inputName" className={`form-control ${codeError ? "is-invalid" : ""}`} placeholder="Input a module code" value={code} onChange={(e) => setCode(e.target.value)} />
                                {codeError && <div className="invalid-feedback">{codeError}</div>}
                            </div>
                            <div className="form-group">
                                <label htmlFor="inputName">Name</label>
                                <input type="text" id="inputName" className={`form-control ${nameError ? "is-invalid" : ""}`} placeholder="Input a module name" value={name} onChange={(e) => setName(e.target.value)} />
                                {nameError && <div className="invalid-feedback">{nameError}</div>}
                            </div>
                            <div className="form-group">
                                <label htmlFor="inputDescription">Description</label>
                                <textarea id="inputDescription" className={`form-control ${descriptionError ? "is-invalid" : ""}`} rows={4} placeholder="Input a description" value={description} onChange={(e) => setDescription(e.target.value)} />
                                {descriptionError && <div className="invalid-feedback">{descriptionError}</div>}
                            </div>
                            <div className="text-center">
                                <button className="btn btn-success mr-2" onClick={handleCreate}>Create</button>
                            </div>
                        </div>
                        {showModal && (
                            <div className="modal fade show" id="modal-default" style={{ display: "block" }}>
                                <div className="modal-dialog">
                                    <div className="modal-content">
                                        <div className="modal-header">
                                            <h4 className="modal-title">Successful Creation of Module</h4>
                                            <button
                                                type="button"
                                                className="close"
                                                data-dismiss="modal"
                                                aria-label="Close"
                                                onClick={() => handleCancel()}>
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                        </div>
                                        <div className="modal-body">
                                            <p>You have successfully created a module!</p>
                                        </div>
                                        <div className="modal-footer justify-content-between">
                                            <button
                                                type="button"
                                                className="btn btn-default"
                                                onClick={() => handleCancel()}>
                                                Close
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        )}
                    </div>
                </div>
            </div>
            <Footer />
        </div>
    )
};

export default PartnerUniversityModule;