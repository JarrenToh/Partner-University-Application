import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

import Footer from '../../../components/dashboard/Footer';
import Header from '../../../components/dashboard/Header';
import Menu from '../../../components/dashboard/Menu';

import API from '../../../../util/API';
import apiPaths from '../../../../util/apiPaths';

const EnquiryDetails = () => {

    const { id } = useParams();
    const [title, setTitle] = useState("");
    const [content, setContent] = useState("");
    const [response, setResponse] = useState("");

    const [showModal, setShowModal] = useState(false);
    const [buttonText, setButtonText] = useState("Submit");

    const navigate = useNavigate();

    useEffect(() => {
        const fetchData = async () => {
            try {
                const apiPath = `${apiPaths.listOfEnquiries}/${id}`
                const response = await API.get(apiPath);
                const data = response.data;

                const title = data.title;
                const content = data.content;

                setTitle(title);
                setContent(content);
                setResponse(data.response);

                setButtonText(Boolean(data.response) ? "Edit" : "Submit");
            } catch (error) {
                console.error(error);
            }
        };
        fetchData();
    }, [id]);

    const handleUpdate = async () => {
        try {
            const updatedEnquiry = {
                title,
                content,
                response
            };

            // TODO: Change to get the adminId dynamically
            const apiPath = `${apiPaths.listOfEnquiries}/${id}/respond?adminId=1`;
            await API.put(apiPath, updatedEnquiry);

            setShowModal(true);
        } catch (error) {
            console.error(error);
        }
    };

    const handleCancel = async () => {
        if (response === undefined) {
            navigate('../enquiries');
        }

        navigate('../enquiries/assigned');
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
                                <textarea id="inputResponse" className="form-control" rows={4} placeholder="Input a response" value={response !== undefined ? response : ""} onChange={(e) => setResponse(e.target.value)} />
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
                                <h4 className="modal-title">Successful {response !== undefined ? "Edit of Response" : "Reply"} to Student's Enquiry</h4>
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
                                <p>You have successfully {response !== undefined ? "edited the response" : "replied"} to a student's enquiry!</p>
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