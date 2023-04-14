import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";

import Header from "../../../components/dashboard/Header";
import Menu from "../../../components/dashboard/Menu";
import Footer from "../../../components/dashboard/Footer";

import API from "../../../../util/API";
import apiPaths from "../../../../util/apiPaths";

import { convertToEncodedTextForUrl } from "../../../../util/urlTextConverter";

const PUModuleDetails = () => {
    const navigate = useNavigate();

    const { puName, puModuleCode } = useParams();
    const [id, setId] = useState(-1);
    const [name, setName] = useState("");
    const [code, setCode] = useState("");
    const [description, setDescription] = useState("");

    const [existingModules, setExistingModules] = useState([]);

    const [showUpdateSuccessModal, setShowUpdateSuccessModal] = useState(false);
    const [showDeleteSuccessModal, setShowDeleteSuccessModal] = useState(false);
    const [showNotFoundModal, setShowNotFoundModal] = useState(false);

    const [nameError, setNameError] = useState("");
    const [codeError, setCodeError] = useState("");
    const [descriptionError, setDescriptionError] = useState("");

    const handleEdit = async () => {
        if (validate()) {
            try {
                const updatedPUModule = {
                    name,
                    code,
                    description,
                };

                const apiPath = `${apiPaths.listOfPUModules}/editPUModuleAdmin/${id}`;
                await API.put(apiPath, updatedPUModule);

                setShowUpdateSuccessModal(true);
            } catch (error) {
                console.error(error);
            }
        }
    };

    const handleDelete = async () => {
        try {
            const apiPath = `${apiPaths.listOfPUModules}/deletePUModuleFromPU/${code}?pu=${convertToEncodedTextForUrl(puName)}`;
            await API.delete(apiPath);

            setShowDeleteSuccessModal(true);
        } catch (error) {
            console.error(error);
        }
    };

    const handleCancelUpdateSuccessModal = () => {
        setShowUpdateSuccessModal(false);
        navigate(`../admin/systemSupportAdmin/partnerUniversities/${puName}/modules/${code}`);
    };

    const handleCancelDeleteSuccessModal = () => {
        navigate(`../admin/systemSupportAdmin/partnerUniversities/${puName}/modules/`);
    };

    const handleCancelNotFoundModal = () => {
        setShowNotFoundModal(false);
        navigate(`../admin/systemSupportAdmin/partnerUniversities/${puName}/modules/`);
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
                const apiPath = `${apiPaths.listOfPUModules}/searchPUModuleByCodeAndPUName?code=${puModuleCode}&name=${convertToEncodedTextForUrl(puName)}`
                const response = await API.get(apiPath);
                const data = response.data;

                if (data.stringStatus === "404") {
                    setShowNotFoundModal(true);
                } else {
                    const id = data.moduleId;
                    const name = data.name;
                    const code = data.code;
                    const description = data.description;

                    setId(id);
                    setName(name);
                    setCode(code);
                    setDescription(description);
                }
            } catch (error) {
                console.error(error);
            }
        };

        const fetchExistingModules = async () => {
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
        fetchExistingModules();
    }, [id, puModuleCode, puName]);

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
                                <label htmlFor="inputName">Code</label>
                                <input type="text" id="inputCode" className={`form-control ${codeError ? "is-invalid" : ""}`} value={code} onChange={(e) => setCode(e.target.value)} />
                                {codeError && <div className="invalid-feedback">{codeError}</div>}
                            </div>
                            <div className="form-group">
                                <label htmlFor="inputName">Name</label>
                                <input type="text" id="inputName" className={`form-control ${nameError ? "is-invalid" : ""}`} value={name} onChange={(e) => setName(e.target.value)} />
                                {nameError && <div className="invalid-feedback">{nameError}</div>}
                            </div>
                            <div className="form-group">
                                <label htmlFor="inputDescription">Description</label>
                                <textarea id="inputDescription" className={`form-control ${descriptionError ? "is-invalid" : ""}`} rows={4} value={description} onChange={(e) => setDescription(e.target.value)} />
                                {descriptionError && <div className="invalid-feedback">{descriptionError}</div>}
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
            {showUpdateSuccessModal && (
                <div className="modal fade show" id="modal-default" style={{ display: "block" }}>
                    <div className="modal-dialog">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h4 className="modal-title">Successful Update of PU Module</h4>
                                <button
                                    type="button"
                                    className="close"
                                    data-dismiss="modal"
                                    aria-label="Close"
                                    onClick={() => handleCancelUpdateSuccessModal()}>
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div className="modal-body">
                                <p>You have successfully updated the PU module!</p>
                            </div>
                            <div className="modal-footer justify-content-between">
                                <button
                                    type="button"
                                    className="btn btn-default"
                                    onClick={() => handleCancelUpdateSuccessModal()}>
                                    Close
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            )}
            {showDeleteSuccessModal && (
                <div className="modal fade show" id="modal-default" style={{ display: "block" }}>
                    <div className="modal-dialog">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h4 className="modal-title">Successful Deletion of PU Module</h4>
                                <button
                                    type="button"
                                    className="close"
                                    data-dismiss="modal"
                                    aria-label="Close"
                                    onClick={() => handleCancelDeleteSuccessModal()}>
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div className="modal-body">
                                <p>You have successfully deleted the PU Module!</p>
                            </div>
                            <div className="modal-footer justify-content-between">
                                <button
                                    type="button"
                                    className="btn btn-default"
                                    onClick={() => handleCancelDeleteSuccessModal()}>
                                    Close
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            )}
            {showNotFoundModal && (
                <div className="modal fade show" id="modal-default" style={{ display: "block" }}>
                    <div className="modal-dialog">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h4 className="modal-title">PU Module Not Found</h4>
                                <button
                                    type="button"
                                    className="close"
                                    data-dismiss="modal"
                                    aria-label="Close"
                                    onClick={() => handleCancelNotFoundModal()}>
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div className="modal-body">
                                <p>The requested PU module could not be found!</p>
                            </div>
                            <div className="modal-footer justify-content-between">
                                <button
                                    type="button"
                                    className="btn btn-default"
                                    onClick={() => handleCancelNotFoundModal()}>
                                    Close
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            )}
            <Footer />
        </div>
    );
};

export default PUModuleDetails;
