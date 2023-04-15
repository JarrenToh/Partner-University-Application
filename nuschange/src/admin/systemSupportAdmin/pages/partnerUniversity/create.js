import React, { useState, useEffect } from 'react';

import Header from '../../../components/dashboard/Header';
import Menu from '../../../components/dashboard/Menu';
import Footer from '../../../components/dashboard/Footer';

import API from '../../../../util/API';
import apiPaths from '../../../../util/apiPaths';

import { Helmet } from 'react-helmet';

const PartnerUniversity = () => {
    const [name, setName] = useState("");
    const [description, setDescription] = useState("");
    const [images, setImages] = useState("");
    const [countryId, setCountryId] = useState("");

    const [countries, setCountries] = useState([]);
    const [existingPUs, setExistingPUs] = useState([]);

    const [nameError, setNameError] = useState("");
    const [descriptionError, setDescriptionError] = useState("");
    const [imagesError, setImagesError] = useState("");
    const [countryIdError, setCountryIdError] = useState("");

    const [showModal, setShowModal] = useState(false);

    const handleCreate = async () => {
        if (validate()) {
            try {
                const createdPU = {
                    name,
                    description,
                    images,
                    countryId
                };

                const apiPath = `${apiPaths.listOfPUs}/createPUWithCountry`;
                await API.post(apiPath, createdPU);

                setShowModal(true);
            } catch (error) {
                console.error(error);
            }
        }
    };

    const handleCancel = async () => {
        window.location.reload();
    };

    const validate = () => {
        let isValid = true;
        if (name.trim() === "") {
            setNameError("Please enter a name");
            isValid = false;
        } else {
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
        const fetchCountries = async () => {
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

        fetchCountries();
        fetchPUs();
    }, []);

    return (
        <div>
            <Helmet>
                <title>Create PU</title>
            </Helmet>
            <Header />
            <Menu />
            <div className="content-wrapper">
                <div className="card">
                    <div className="card card-primary">
                        <div className="card-header">
                            <h3 className="card-title">Create Partner University</h3>
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
                                <label htmlFor="inputName">Images</label> {/* TODO: need to change to upload image */}
                                <input type="text" id="inputName" className={`form-control ${imagesError ? "is-invalid" : ""}`} placeholder="Input a image" value={images} onChange={(e) => setImages(e.target.value)} />
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
                                <button className="btn btn-success mr-2" onClick={handleCreate}>Create</button>
                            </div>
                        </div>
                        {showModal && (
                            <div className="modal fade show" id="modal-default" style={{ display: "block" }}>
                                <div className="modal-dialog">
                                    <div className="modal-content">
                                        <div className="modal-header">
                                            <h4 className="modal-title">Successful Creation of Partner University</h4>
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
                                            <p>You have successfully created the Partner University!</p>
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

export default PartnerUniversity;