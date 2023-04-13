import React, { useState, useEffect, useContext } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

import Footer from '../../../components/dashboard/Footer';
import Header from '../../../components/dashboard/Header';
import Menu from '../../../components/dashboard/Menu';

import API from '../../../../util/API';
import apiPaths from '../../../../util/apiPaths';
import { AuthContext } from '../../../../AuthContext';

const EnquiryDetails = () => {

    const { id } = useParams();
    const [title, setTitle] = useState("");
    const [content, setContent] = useState("");
    const [status, setStatus] = useState("");
    const [response, setResponse] = useState("");

    const [showModal, setShowModal] = useState(false);
    const [buttonText, setButtonText] = useState("Submit");
    const [successfulText, setSuccessfulText] = useState("");
    const [successfulResponseText, setSuccessfulResposneText] = useState("");

    const [responseError, setResponseError] = useState("");

    const navigate = useNavigate();

    const { loggedInAdmin } = useContext(AuthContext);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const apiPath = `${apiPaths.listOfEnquiries}/${id}`
                const test = await API.get(apiPath);
                const data = test.data;

                const title = data.title;
                const content = data.content;
                const status = data.status;

                setTitle(title);
                setContent(content);
                setStatus(status);

                if (status !== "PENDING") {
                    setResponse(data.response);
                    setSuccessfulText("Edit of Response");
                    setSuccessfulResposneText("edited the response");
                } else {
                    setSuccessfulText("Reply");
                    setSuccessfulResposneText("replied");
                }

                setButtonText(Boolean(data.response) ? "Edit" : "Submit");
            } catch (error) {
                console.error(error);
            }
        };
        fetchData();
    }, [id]);

    const handleUpdate = async () => {
        if (validate()) {
            try {
                const updatedEnquiry = {
                    title,
                    content,
                    response
                };

                const apiPath = `${apiPaths.listOfEnquiries}/${id}/respond?adminId=${loggedInAdmin.adminId}`;
                await API.put(apiPath, updatedEnquiry);

                setShowModal(true);
            } catch (error) {
                console.error(error);
            }
        }
    };

    const handleCancel = async () => {
        if (status === "PENDING") {
            navigate('../enquiries');
            return;
        }

        navigate('../enquiries/assigned');
    };

    const validate = () => {
        let isValid = true;
        if (response.trim() === "") {
            setResponseError("Please enter a response");
            isValid = false;
        } else {
            setResponseError("");
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
                            <h3 className="card-title">View Enquiry Details</h3>
                        </div>
                        <div className="card-body">
                            <div className="form-group">
                                <label htmlFor="inputTitle">Title</label>
                                <input type="text" id="inputTitle" className="form-control" value={title} readOnly />
                            </div>
                            <div className="form-group">
                                <label htmlFor="inputContent">Content</label>
                                <input type="text" id="inputContent" className="form-control" value={content} readOnly />
                            </div>
                            <div className="form-group">
                                <label htmlFor="inputDescription">Response</label>
                                <textarea id="inputResponse" className={`form-control ${responseError ? "is-invalid" : ""}`} rows={4} placeholder="Input a response" value={response} onChange={(e) => setResponse(e.target.value)} />
                                {responseError && <div className="invalid-feedback">{responseError}</div>}
                            </div>
                            <div className="text-center">
                                <button className="btn btn-success mr-2" onClick={handleUpdate}>{buttonText}</button>
                            </div>
                        </div>
                        <br />
                    </div>
                </div>
            </div>
            {showModal && (
                <div className="modal fade show" id="modal-default" style={{ display: "block" }}>
                    <div className="modal-dialog">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h4 className="modal-title">Successful {successfulText} to Student's Enquiry</h4>
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
                                <p>You have successfully {successfulResponseText} to a student's enquiry!</p>
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
            <Footer />
        </div>
    )
};

export default EnquiryDetails;