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
    const [puId, setPuId] = useState("");
    const [isInappropriate, setIsInappropriate] = useState(false);

    const [pus, setPUs] = useState([]);

    const [showUpdateSuccessModal, setShowUpdateSuccessModal] = useState(false);
    const [showDeleteSuccessModal, setShowDeleteSuccessModal] = useState(false);
    const [showNotFoundModal, setShowNotFoundModal] = useState(false);
    const [showNotAdminModal, setShowNotAdminModal] = useState(false);

    const [topicNameError, setTopicNameError] = useState("");
    const [puIdError, setPuIdError] = useState("");

    const handleEdit = async () => {
        if (await validate()) {
            try {
                const updatedForumTopic = {
                    topicName,
                    puId,
                    isInappropriate
                };

                const apiPath = `${apiPaths.listOfAdminForumTopics}/${id}`;
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
        navigate('../admin/systemSupportAdmin/forumTopics');
    };

    const handleCancelNotFoundModal = () => {
        setShowNotFoundModal(false);
        navigate('../admin/systemSupportAdmin/forumTopics');
    };

    const validate = async () => {
        let isValid = true;
        if (topicName.trim() === "") {
            setTopicNameError("Please enter a topic name");
            isValid = false;
        } else {
            if (puId !== "") {
                const apiPath = `${apiPaths.listOfAdminForumTopics}/searchForumTopicsByPuAdmin/${puId}`
                const response = await API.get(apiPath);
                const forumTopics = response.data;

                const duplicateTopicName = forumTopics.some(
                    (forumTopic) => forumTopic.topicName.toLowerCase() === topicName.toLowerCase()
                );

                if (duplicateTopicName) {
                    setTopicNameError("Topic name already exists in this PU");
                    isValid = false;
                } else {
                    setTopicNameError("");
                }
            }
        }
        if (puId === "") {
            setPuIdError("Please select a partner university");
            isValid = false;
        } else {
            setPuIdError("");
        }

        return isValid;
    };

    useEffect(() => {
        const fetchAdminTopicData = async () => {
            try {
                const apiPath = `${apiPaths.listOfAdminForumTopics}/${id}`
                const response = await API.get(apiPath);
                const data = response.data;

                if (data.stringStatus === "404") {
                    setShowNotFoundModal(true);
                } else if (data.adminId === -1) {
                    setShowNotAdminModal(true);
                } else {
                    const topicName = data.topicName;
                    const puId = data.puId;
                    const isInappropriate = data.isInappropriate;

                    setTopicName(topicName);
                    setPuId(puId);
                    setIsInappropriate(isInappropriate);
                }
            } catch (error) {
                console.error(error);
            }
        };

        const fetchPUsData = async () => {
            try {
                const apiPath = `${apiPaths.listOfPUs}`
                const response = await API.get(apiPath);
                const data = response.data;

                setPUs(data);
            } catch (error) {
                console.error(error);
            }
        };

        fetchAdminTopicData();
        fetchPUsData();
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
                                <input type="text" id="inputName" className={`form-control ${topicNameError ? "is-invalid" : ""}`} value={topicName} onChange={(e) => setTopicName(e.target.value)} />
                                {topicNameError && <div className="invalid-feedback">{topicNameError}</div>}
                            </div>
                            <div className="form-group">
                                <label htmlFor="inputName">Partner Universities</label>
                                <select className={`custom-select rounded-0 form-control ${puIdError ? "is-invalid" : ""}`} id="puSelectOption" value={puId} onChange={(e) => setPuId(e.target.value)}>
                                    <option value="">Select Partner University</option>
                                    {pus.map((pu, index) => {
                                        return (
                                            <option key={index} value={pu.puId}>
                                                {pu.name}
                                            </option>
                                        );
                                    })}
                                </select>
                                {puIdError && <div className="invalid-feedback">{puIdError}</div>}
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
            {showNotFoundModal && (
                <div className="modal fade show" id="modal-default" style={{ display: "block" }}>
                    <div className="modal-dialog">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h4 className="modal-title">Forum Topic Not Found</h4>
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
                                <p>The requested forum topic could not be found!</p>
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
            {showNotAdminModal && (
                <div className="modal fade show" id="modal-default" style={{ display: "block" }}>
                    <div className="modal-dialog">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h4 className="modal-title">Forum Topic by Student</h4>
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
                                <p>The requested forum topic is created by student!</p>
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

export default ForumTopicDetails;
