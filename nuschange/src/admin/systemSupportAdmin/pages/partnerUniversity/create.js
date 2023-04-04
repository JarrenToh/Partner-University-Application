import React, { useState, useEffect } from 'react';

import Header from '../../../components/dashboard/Header';
import Menu from '../../../components/dashboard/Menu';
import Footer from '../../../components/dashboard/Footer';

import API from '../../../../util/API';
import apiPaths from '../../../../util/apiPaths';

const PartnerUniversity = () => {
    const [name, setName] = useState("");
    const [description, setDescription] = useState("");
    const [images, setImages] = useState("");
    const [countryId, setCountryId] = useState(-1);

    const [countries, setCountries] = useState([]);

    const handleCreate = async () => {
        try {
            const createdPU = {
                name,
                description,
                images,
                countryId
            };

            const apiPath = `${apiPaths.listOfPUs}/createPUWithCountry`;
            await API.post(apiPath, createdPU);

            alert("PU has been created successfully");
        } catch (error) {
            console.error(error);
        }
    };

    useEffect(() => {
        const fetchData = async () => {
            try {
                const apiPath = `${apiPaths.listOfCountries}`
                const response = await API.get(apiPath);
                const data = response.data;

                setCountries(data);
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
                    <div className="card card-primary">
                        <div className="card-header">
                            <h3 className="card-title">Create Partner University (PU)</h3>
                        </div>
                        <div className="card-body">
                            <div className="form-group">
                                <label htmlFor="inputName">Name</label>
                                <input type="text" id="inputName" className="form-control" placeholder="Input a name" onChange={(e) => setName(e.target.value)} />
                            </div>
                            <div className="form-group">
                                <label htmlFor="inputDescription">Description</label>
                                <textarea id="inputDescription" className="form-control" rows={4} placeholder="Input a description" onChange={(e) => setDescription(e.target.value)} />
                            </div>
                            <div className="form-group">
                                <label htmlFor="inputName">Images</label> {/* TODO: need to change to upload image */}
                                <input type="text" id="inputName" className="form-control" placeholder="Input a image" onChange={(e) => setImages(e.target.value)} />
                            </div>
                            <div className="form-group">
                                <label htmlFor="inputName">Country</label>
                                <select className="custom-select rounded-0" id="countrySelectOption" onChange={(e) => setCountryId(e.target.value)}>
                                    <option value="">Select Country</option>
                                    {countries.map((country) => {
                                        return (
                                            <option key={country.countryId} value={country.countryId}>
                                                {country.name}
                                            </option>
                                        );
                                    })}
                                </select>
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

export default PartnerUniversity;