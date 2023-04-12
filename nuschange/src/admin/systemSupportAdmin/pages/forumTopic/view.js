import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";

import Header from "../../../components/dashboard/Header";
import Menu from "../../../components/dashboard/Menu";
import Footer from "../../../components/dashboard/Footer";

import API from "../../../../util/API";
import apiPaths from "../../../../util/apiPaths";

const ForumTopicDetails = () => {
    const navigate = useNavigate();
    const { id } = useParams();
    const [topicName, setTopicName] = useState("");
    const [isInappropriate, setIsInappropriate] = useState(false);

    const [showUpdateSuccessModal, setShowUpdateSuccessModal] = useState(false);
    const [showDeleteSuccessModal, setShowDeleteSuccessModal] = useState(false);

    const [topicNameError, setTopicNameError] = useState("");

    const handleEdit = async () => {
        if (validate()) {
            try {
                const updatedForumTopic = {
                    topicName,
                    isInappropriate
                };

                // TODO: Change to get the adminId dynamically
                const apiPath = `${apiPaths.listOfForumTopics}/editForumTopicByAdmin/${id}?adminId=1`;
                await API.put(apiPath, updatedForumTopic);

                setShowUpdateSuccessModal(true);
            } catch (error) {
                console.error(error);
            }
        }
    };

    const handleDelete = async () => {
        try {
            const apiPath = `${apiPaths.listOfForumTopics}/${id}`;
            await API.delete(apiPath);
            
            setShowDeleteSuccessModal(true);
        } catch (error) {
            console.error(error);
        }
    };

    const handleCancelUpdateSuccessModal = () => {
        setShowUpdateSuccessModal(false);
    };

    const handleCancelDeleteSuccessModal = () => {
        navigate('../forumTopics');
    };

    const validate = () => {
        let isValid = true;
        if (topicName.trim() === "") {
            setTopicNameError("Please enter a topic name");
            isValid = false;
        } else {
            setTopicNameError("");
        }

        return isValid;
    }

    useEffect(() => {
        const fetchData = async () => {
            try {
                const apiPath = `${apiPaths.listOfForumTopics}/${id}`
                const response = await API.get(apiPath);
                const data = response.data;

                const topicName = data.topicName;
                const isInappropriate = data.isInappropriate;

                setTopicName(topicName);
                setIsInappropriate(isInappropriate);
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
                            <h3 className="card-title">View Forum Topic Details</h3>
                        </div>
                        <div className="card-body">
                            <div className="form-group">
                                <label htmlFor="inputName">Topic Name</label>
                                <input type="text" id="inputName" className={`form-control ${topicNameError ? "is-invalid" : ""}`}  value={topicName} onChange={(e) => setTopicName(e.target.value)} />
                                {topicNameError && <div className="invalid-feedback">{topicNameError}</div>}
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
                                <h4 className="modal-title">Successful Update of Forum Topic</h4>
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
                                <p>You have successfully updated the forum topic!</p>
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
                                <h4 className="modal-title">Successful Deletion of Forum Topic</h4>
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
                                <p>You have successfully deleted the forum topic!</p>
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
            <Footer />
        </div>
    );
};

export default ForumTopicDetails;
