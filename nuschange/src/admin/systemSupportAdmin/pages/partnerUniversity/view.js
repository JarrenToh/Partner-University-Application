import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";

import Header from "../../../components/dashboard/Header";
import Menu from "../../../components/dashboard/Menu";
import Footer from "../../../components/dashboard/Footer";

import API from "../../../../util/API";
import apiPaths from "../../../../util/apiPaths";
import { convertToEncodedTextForUrl, convertNameToSlug } from "../../../../util/urlTextConverter";

import { Helmet } from "react-helmet";

const PUDetails = () => {
    const navigate = useNavigate();

    const { nameFromUrl } = useParams();
    const [id, setId] = useState(-1);
    const [name, setName] = useState("");
    const [currentName, setCurrentName] = useState("");
    const [description, setDescription] = useState("");
    const [images, setImages] = useState("");
    const [currentImages, setCurrentImages] = useState("");
    const [countryId, setCountryId] = useState("");

    const [countries, setCountries] = useState([]);
    const [existingPUs, setExistingPUs] = useState([]);

    const [showUpdateSuccessModal, setShowUpdateSuccessModal] = useState(false);
    const [showDeleteSuccessModal, setShowDeleteSuccessModal] = useState(false);
    const [showNotFoundModal, setShowNotFoundModal] = useState(false);

    const [nameError, setNameError] = useState("");
    const [descriptionError, setDescriptionError] = useState("");
    const [imagesError, setImagesError] = useState("");
    const [countryIdError, setCountryIdError] = useState("");

    // TODO: handle edit for image as well once we using the image data
    const handleEdit = async () => {
        if (validate()) {
            try {
                const updatedPU = {
                    name,
                    description,
                    images,
                    countryId
                };

                const apiPath = `${apiPaths.listOfPUs}/${id}`;
                await API.put(apiPath, updatedPU);

                setShowUpdateSuccessModal(true);
            } catch (error) {
                console.error(error);
            }
        }
    };

    const handleDelete = async () => {
        try {
            const apiPath = `${apiPaths.listOfPUs}/${id}`;
            await API.delete(apiPath);

            setShowDeleteSuccessModal(true);
        } catch (error) {
            console.error(error);
        }
    };

    const handleCancelUpdateSuccessModal = () => {
        setShowUpdateSuccessModal(false);
        navigate(`../admin/systemSupportAdmin/partnerUniversities/${convertNameToSlug(name)}`);
    };

    const handleCancelDeleteSuccessModal = () => {
        navigate('../admin/systemSupportAdmin/partnerUniversities');
    };

    const handleCancelNotFoundModal = () => {
        setShowNotFoundModal(false);
        navigate('../admin/systemSupportAdmin/partnerUniversities');
    };

    const validate = () => {
        let isValid = true;
        if (name.trim() === "") {
            setNameError("Please enter a name");
            isValid = false;
        } else {
            if (name.trim() !== currentName) {
                const duplicateName = existingPUs.some(
                    (pu) => pu.name.toLowerCase() === name.toLowerCase()
                );

                if (duplicateName) {
                    setNameError("PU Name already exists");
                    isValid = false;
                } else {
                    setNameError("");
                }
            }
        }
        if (description.trim() === "") {
            setDescriptionError("Please enter a description");
            isValid = false;
        } else {
            setDescriptionError("");
        }
        if (images.trim() === "") {
            setImagesError("Please enter a image");
            isValid = false;
        } else {
            setImagesError("");
        }
        if (countryId === "") {
            setCountryIdError("Please select a country");
            isValid = false;
        } else {
            setCountryIdError("");
        }
        return isValid;
    };

    useEffect(() => {
        const fetchPUData = async () => {
            try {
                const apiPath = `${apiPaths.listOfPUs}/getPUByNameAdmin/${convertToEncodedTextForUrl(nameFromUrl)}`
                const response = await API.get(apiPath);
                const data = response.data;

                if (data.stringStatus === "404") {
                    setShowNotFoundModal(true);
                } else {
                    const id = data.puId;
                    const name = data.name;
                    const description = data.description;
                    const images = data.images;
                    const countryId = data.countryId;

                    setId(id);
                    setName(name);
                    setCurrentName(name);
                    setDescription(description);
                    setImages(images);
                    setCurrentImages(images);
                    setCountryId(countryId);
                }
            } catch (error) {
                console.error(error);
            }
        };

        const fetchCountriesData = async () => {
            try {
                const apiPath = `${apiPaths.listOfCountries}`
                const response = await API.get(apiPath);
                const data = response.data;

                setCountries(data);
            } catch (error) {
                console.error(error);
            }
        };

        const fetchPUs = async () => {
            try {
                const apiPath = `${apiPaths.listOfPUs}`
                const response = await API.get(apiPath);
                const data = response.data;

                setExistingPUs(data);
            } catch (error) {
                console.error(error);
            }
        };

        fetchPUData();
        fetchCountriesData();
        fetchPUs();
    }, [nameFromUrl]);

    return (
        <div>
            <Helmet>
                <title>View PU Details</title>
            </Helmet>
            <Header />
            <Menu />
            <div className="content-wrapper">
                <div className="card">
                    <div className="card card-primary">
                        <div className="card-header">
                            <h3 className="card-title">View Partner University Details</h3>
                        </div>
                        <div className="card-body">
                            <div className="form-group">
                                <label htmlFor="inputName">Name</label>
                                <input type="text" id="inputName" className={`form-control ${nameError ? "is-invalid" : ""}`} placeholder="Input a name" value={name} onChange={(e) => setName(e.target.value)} />
                                {nameError && <div className="invalid-feedback">{nameError}</div>}
                            </div>
                            <div className="form-group">
                                <label htmlFor="inputDescription">Description</label>
                                <textarea id="inputDescription" className={`form-control ${descriptionError ? "is-invalid" : ""}`} rows={4} placeholder="Input a description" value={description} onChange={(e) => setDescription(e.target.value)} />
                                {descriptionError && <div className="invalid-feedback">{descriptionError}</div>}
                            </div>
                            <div className="form-group">
                                <label htmlFor="inputName">Image</label>
                                <input type="text" id="inputName" className={`form-control ${imagesError ? "is-invalid" : ""}`} placeholder="Input a image" value={images} onChange={(e) => setImages(e.target.value)} />
                                <br />
                                <img src={currentImages} alt="" style={{ maxWidth: "100%" }} />
                                {imagesError && <div className="invalid-feedback">{imagesError}</div>}
                            </div>
                            <div className="form-group">
                                <label htmlFor="inputName">Country</label>
                                <select className={`custom-select rounded-0 form-control ${countryIdError ? "is-invalid" : ""}`} id="countrySelectOption" value={countryId} onChange={(e) => setCountryId(e.target.value)}>
                                    <option value="">Select Country</option>
                                    {countries.map((country, index) => {
                                        return (
                                            <option key={index} value={country.countryId}>
                                                {country.name}
                                            </option>
                                        );
                                    })}
                                </select>
                                {countryIdError && <div className="invalid-feedback">{countryIdError}</div>}
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
            {showUpdateSuccessModal && (
                <div className="modal fade show" id="modal-default" style={{ display: "block" }}>
                    <div className="modal-dialog">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h4 className="modal-title">Successful Update of Partner University</h4>
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
                                <p>You have successfully updated the Partner University!</p>
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
                                <h4 className="modal-title">Successful Deletion of Partner University</h4>
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
                                <p>You have successfully deleted the Partner University!</p>
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
                                <h4 className="modal-title">Partner University Not Found</h4>
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
                                <p>The requested partner university could not be found!</p>
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

export default PUDetails;
