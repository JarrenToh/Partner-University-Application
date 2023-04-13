import React, { useContext, useState } from 'react';

import Header from '../../../components/dashboard/Header';
import Menu from '../../../components/dashboard/Menu';
import Footer from '../../../components/dashboard/Footer';

import API from '../../../../util/API';
import apiPaths from '../../../../util/apiPaths';
import { AuthContext } from '../../../../AuthContext';

const ForumTopic = () => {
    const [topicName, setTopicName] = useState("");

    const [topicNameError, setTopicNameError] = useState("");

    const [showModal, setShowModal] = useState(false);

    const { loggedInAdmin } = useContext(AuthContext);

    const handleCreate = async () => {
        if (validate()) {
            try {
                const createForumTopic = {
                    topicName
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
        setTopicName("");
        setShowModal(false);
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
    };

    return (
        <div>
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