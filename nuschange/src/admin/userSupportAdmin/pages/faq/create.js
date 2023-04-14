import React, { useContext, useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

import Header from '../../../components/dashboard/Header';
import Menu from '../../../components/dashboard/Menu';
import Footer from '../../../components/dashboard/Footer';

import API from '../../../../util/API';
import apiPaths from '../../../../util/apiPaths';
import { AuthContext } from '../../../../AuthContext';

import { Helmet } from 'react-helmet';

const FAQ = () => {
    const navigate = useNavigate();
    const [question, setQuestion] = useState("");
    const [answer, setAnswer] = useState("");
    const [showModal, setShowModal] = useState(false);

    const [existingFAQs, setExistingFAQs] = useState([]);

    const [questionError, setQuestionError] = useState("");
    const [answerError, setAnswerError] = useState("");

    const { loggedInAdmin } = useContext(AuthContext);

    const handleCreate = async () => {
        if (await validate()) {
            try {
                const createdFAQ = {
                    question,
                    answer
                };

                const apiPath = `${apiPaths.listOfFaqs}?adminId=${loggedInAdmin.adminId}`;
                await API.post(apiPath, createdFAQ);

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
        const fetchFAQs = async () => {
            try {
                const apiPath = `${apiPaths.listOfFaqs}`;
                const response = await API.get(apiPath);
                setExistingFAQs(response.data);
            } catch (error) {
                console.error(error);
            }
        };

        fetchFAQs();
    }, []);

    return (
        <div>
            <Helmet>
                <title>Create FAQ</title>
            </Helmet>
            <Header />
            <Menu />
            <div className="content-wrapper">
                <div className="card">
                    <div className="card card-primary">
                        <div className="card-header">
                            <h3 className="card-title">Create FAQ</h3>
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
                                <button className="btn btn-success mr-2" onClick={handleCreate}>Create</button>
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
                                <h4 className="modal-title">Successful Creation of FAQ</h4>
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
                                <p>You have successfully created the FAQ!</p>
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

export default FAQ;
