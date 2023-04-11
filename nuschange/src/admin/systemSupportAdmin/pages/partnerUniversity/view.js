import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";

import Header from "../../../components/dashboard/Header";
import Menu from "../../../components/dashboard/Menu";
import Footer from "../../../components/dashboard/Footer";

import API from "../../../../util/API";
import apiPaths from "../../../../util/apiPaths";
import { convertToEncodedTextForUrl, convertNameToSlug } from "../../../../util/urlTextConverter";

const PUDetails = () => {
    const navigate = useNavigate();

    const { nameFromUrl } = useParams();
    const [id, setId] = useState(-1);
    const [name, setName] = useState("");
    const [description, setDescription] = useState("");
    const [images, setImages] = useState("");

    const redirectedUrl = `${`/partnerUniversities`}`;

    const alertAndNavigate = (keyword) => {
        alert(`PU has been ${keyword} successfully`);
        navigate(redirectedUrl);
    }

    // TODO: handle edit for image as well once we using the image data
    const handleEdit = async () => {
        try {
            const updatedPU = {
                name,
                description,
                images
            };
            
            const apiPath = `${apiPaths.listOfPUs}/${id}`;
            await API.put(apiPath, updatedPU);

            alertAndNavigate("updated");
        } catch (error) {
            console.error(error);
        }
    };

    const handleDelete = async () => {
        try {
            const apiPath = `${apiPaths.listOfPUs}/${id}`;
            await API.delete(apiPath);

            alertAndNavigate("deleted");
        } catch (error) {
            console.error(error);
        }
    };

    useEffect(() => {
        const fetchData = async () => {
            try {
                const apiPath = `${apiPaths.listOfPUs}/getPUByName/${convertToEncodedTextForUrl(nameFromUrl)}`
                const response = await API.get(apiPath);
                const data = response.data;

                const id = data.puId;
                const name = data.name;
                const description = data.description;
                const images = data.images;

                setId(id);
                setName(name);
                setDescription(description);
                setImages(images);
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
                            <h3 className="card-title">View PU Details</h3>
                        </div>
                        <div className="card-body">
                            <div className="form-group">
                                <label htmlFor="inputName">Name</label>
                                <input type="text" id="inputName" className="form-control" value={name} onChange={(e) => setName(e.target.value)} />
                            </div>
                            <div className="form-group">
                                <label htmlFor="inputDescription">Description</label>
                                <textarea id="inputDescription" className="form-control" rows={4} value={description} onChange={(e) => setDescription(e.target.value)} />
                            </div>
                            <div className="form-group">
                                <label htmlFor="inputImage">Image</label>
                                <br />
                                <img src={images} alt="PU Image" style={{ maxWidth: "100%" }} />
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
    );
};

export default PUDetails;
