import React, { useContext, useState, useEffect } from 'react';

import Header from '../../../components/dashboard/Header';
import Menu from '../../../components/dashboard/Menu';
import Footer from '../../../components/dashboard/Footer';

import API from '../../../../util/API';
import apiPaths from '../../../../util/apiPaths';
import { AuthContext } from '../../../../AuthContext';

import { Helmet } from 'react-helmet';

const ForumTopic = () => {
    const [topicName, setTopicName] = useState("");
    const [puId, setPuId] = useState("");

    const [pus, setPUs] = useState([]);

    const [topicNameError, setTopicNameError] = useState("");
    const [puIdError, setPuIdError] = useState("");

    const [showModal, setShowModal] = useState(false);

    const { loggedInAdmin } = useContext(AuthContext);

    const handleCreate = async () => {
        if (await validate()) {
            try {
                const createForumTopic = {
                    topicName,
                    puId
                };

                const apiPath = `${apiPaths.listOfAdminForumTopics}?adminId=${loggedInAdmin.adminId}`;
                await API.post(apiPath, createForumTopic);

                setShowModal(true);
            } catch (error) {
                console.error(error);
            }
        }
    };

    const handleCancel = async () => {
        window.location.reload();
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
        const fetchData = async () => {
            try {
                const apiPath = `${apiPaths.listOfPUs}`
                const response = await API.get(apiPath);
                const data = response.data;

                setPUs(data);
            } catch (error) {
                console.error(error);
            }
        };

        fetchData();
    }, []);

    return (
        <div>
            <Helmet>
                <title>Create Forum Topic</title>
            </Helmet>
            <Header />
            <Menu />
            <div className="content-wrapper">
                <div className="card">
                    <div className="card card-primary">
                        <div className="card-header">
                            <h3 className="card-title">Create Forum Topic</h3>
                        </div>
                        <div className="card-body">
                            <div className="form-group">
                                <label htmlFor="inputName">Topic Name</label>
                                <input type="text" id="inputName" className={`form-control ${topicNameError ? "is-invalid" : ""}`} value={topicName} placeholder="Input a topic Name" onChange={(e) => setTopicName(e.target.value)} />
                                {topicNameError && <div className="invalid-feedback">{topicNameError}</div>}
                            </div>
                            <div className="form-group">
                                <label htmlFor="inputName">Partner University</label>
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
                                <button className="btn btn-success mr-2" onClick={handleCreate}>Create</button>
                            </div>
                        </div>
                        {showModal && (
                            <div className="modal fade show" id="modal-default" style={{ display: "block" }}>
                                <div className="modal-dialog">
                                    <div className="modal-content">
                                        <div className="modal-header">
                                            <h4 className="modal-title">Successful Creation of Forum Topic</h4>
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
                                            <p>You have successfully created the forum topic!</p>
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

export default ForumTopic;