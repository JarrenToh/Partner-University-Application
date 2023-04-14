import React, { useState, useEffect, useContext } from "react";
import { useNavigate, useParams } from "react-router-dom";

import Header from "../../../components/dashboard/Header";
import Menu from "../../../components/dashboard/Menu";
import Footer from "../../../components/dashboard/Footer";

import API from "../../../../util/API";
import apiPaths from "../../../../util/apiPaths";
import { AuthContext } from "../../../../AuthContext";

import { Helmet } from "react-helmet";

const FAQDetails = () => {
    const { id } = useParams();
    const [question, setQuestion] = useState("");
    const [answer, setAnswer] = useState("");

    const [existingFAQs, setExistingFAQs] = useState([]);

    const [showUpdateSuccessModal, setShowUpdateSuccessModal] = useState(false);
    const [showDeleteSuccessModal, setShowDeleteSuccessModal] = useState(false);
    const [showNotFoundModal, setShowNotFoundModal] = useState(false);

    const [questionError, setQuestionError] = useState("");
    const [answerError, setAnswerError] = useState("");

    const navigate = useNavigate();

    const { loggedInAdmin } = useContext(AuthContext);

    const handleEdit = async () => {
        if (validate()) {
            try {
                const updatedFAQ = {
                    question,
                    answer
                };

                const apiPath = `${apiPaths.listOfFaqs}/${id}?adminId=${loggedInAdmin.adminId}`;
                await API.put(apiPath, updatedFAQ);

                setShowUpdateSuccessModal(true);
            } catch (error) {
                console.error(error);
            }
        }
    };

    const handleDelete = async () => {
        try {
            const apiPath = `${apiPaths.listOfFaqs}/${id}`;
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
        navigate('../admin/userSupportAdmin/faqs');
    };

    const handleCancelNotFoundModal = () => {
        setShowNotFoundModal(false);
        navigate('../admin/userSupportAdmin/faqs');
    };

    const validate = () => {
        let isValid = true;
        if (question.trim() === "") {
            setQuestionError("Please enter a question");
            isValid = false;
        } else {
            const duplicateQuestion = existingFAQs.some(
                (faq) => faq.question.toLowerCase() === question.toLowerCase()
            );

            if (duplicateQuestion) {
                setQuestionError("Question already exists");
                isValid = false;
            } else {
                setQuestionError("");
            }
        }
        if (answer.trim() === "") {
            setAnswerError("Please enter an answer");
            isValid = false;
        } else {
            setAnswerError("");
        }
        return isValid;
    };

    useEffect(() => {
        const fetchData = async () => {
            try {
                const apiPath = `${apiPaths.listOfFaqs}/${id}`
                const response = await API.get(apiPath);
                const data = response.data;

                if (data.stringStatus === "404") {
                    setShowNotFoundModal(true);
                } else {
                    const question = data.question;
                    const answer = data.answer;

                    setQuestion(question);
                    setAnswer(answer);
                }
            } catch (error) {
                console.error(error);
            }
        };

        const fetchFAQs = async () => {
            try {
                const apiPath = `${apiPaths.listOfFaqs}`;
                const response = await API.get(apiPath);
                setExistingFAQs(response.data);
            } catch (error) {
                console.error(error);
            }
        };

        fetchData();
        fetchFAQs();
    }, [id]);

    return (
        <div>
            <Helmet>
                <title>View FAQ Details</title>
            </Helmet>
            <Header />
            <Menu />
            <div className="content-wrapper">
                <div className="card">
                    <div className="card card-primary">
                        <div className="card-header">
                            <h3 className="card-title">View FAQ Details</h3>
                        </div>
                        <div className="card-body">
                            <div className="form-group">
                                <label htmlFor="inputName">Question</label>
                                <input type="text" id="inputName" className={`form-control ${questionError ? "is-invalid" : ""}`} placeholder="Input a question" onChange={(e) => setQuestion(e.target.value)} value={question} />
                                {questionError && <div className="invalid-feedback">{questionError}</div>}
                            </div>
                            <div className="form-group">
                                <label htmlFor="inputDescription">Answer</label>
                                <textarea id="inputDescription" className={`form-control ${answerError ? "is-invalid" : ""}`} rows={4} placeholder="Input an answer" onChange={(e) => setAnswer(e.target.value)} value={answer} />
                                {answerError && <div className="invalid-feedback">{answerError}</div>}
                            </div>
                            <div className="text-center">
                                <button className="btn btn-success mr-2" onClick={handleEdit}>Save Changes</button>
                                <button className="btn btn-danger" onClick={handleDelete}>Delete</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            {showUpdateSuccessModal && (
                <div className="modal fade show" id="modal-default" style={{ display: "block" }}>
                    <div className="modal-dialog">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h4 className="modal-title">Successful Update of FAQ</h4>
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
                                <p>You have successfully updated the FAQ!</p>
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
                                <h4 className="modal-title">Successful Deletion of FAQ</h4>
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
                                <p>You have successfully deleted the FAQ!</p>
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
                                <h4 className="modal-title">FAQ Not Found</h4>
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
                                <p>The requested FAQ could not be found!</p>
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

export default FAQDetails;
