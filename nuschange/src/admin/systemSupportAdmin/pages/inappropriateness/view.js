import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";

import Header from "../../../components/dashboard/Header";
import Menu from "../../../components/dashboard/Menu";
import Footer from "../../../components/dashboard/Footer";

import API from "../../../../util/API";
import apiPaths from "../../../../util/apiPaths";

const InappropriatenessDetails = () => {
    const navigate = useNavigate();
    const { typeOfComponent, id } = useParams();

    // forumComment & forumPost
    const [message, setMessage] = useState("");
    // puReview & puModuleReview
    const [review, setReview] = useState("");
    // puReview
    const [rating, setRating] = useState("");
    // forumTopic
    const [topicName, setTopicName] = useState("");
    // forumComment
    const [title, setTitle] = useState("");
    // general except forumTopic 
    const [noOfDislikes, setNoOfDislikes] = useState(undefined);
    const [noOfLikes, setNoOfLikes] = useState(undefined);
    // general
    const [studentName, setStudentName] = useState("");
    const [labelOfText, setLabelOfText] = useState("");
    const [headerMessage, setHeaderMessage] = useState("");
    const [outputOfText, setOutputOfText] = useState("");

    const [selectValue, setSelectValue] = useState("");
    const [approvalError, setApprovalError] = useState("");

    const [showAppropriateModal, setShowAppropriateModal] = useState(false);
    const [showNotFoundModal, setShowNotFoundModal] = useState(false);
    const [showModal, setShowModal] = useState(false);
    const [modalTitle, setModalTitle] = useState("");
    const [modalDescription, setModalDescription] = useState("");


    const handleResponse = async () => {
        if (validate()) {
            try {
                let apiPath = "";
                let updateApiPath = "";
                let updatedElement = {};

                switch (typeOfComponent) {
                    case "forumComments":
                        apiPath = `${apiPaths.listOfForumComments}/${id}`;
                        updateApiPath = `${apiPaths.listOfForumComments}/editForumCommentByAdmin/${id}`;
                        updatedElement = {
                            message,
                            noOfLikes,
                            noOfDislikes,
                            isInappropriate: false
                        };
                        break;
                    case "puReviews":
                        apiPath = `${apiPaths.listOfPUReviews}/${id}`;
                        updateApiPath = `${apiPaths.listOfPUReviews}/${id}`;
                        updatedElement = {
                            rating,
                            review,
                            noOfLikes,
                            noOfDislikes,
                            isInappropriate: false
                        };
                        break;
                    case "forumTopics":
                        apiPath = `${apiPaths.listOfForumTopics}/${id}`;
                        updateApiPath = `${apiPaths.listOfForumTopics}/editForumTopicByAdmin/${id}`;
                        updatedElement = {
                            topicName,
                            isInappropriate: false
                        };
                        break;
                    case "puModuleReviews":
                        apiPath = `${apiPaths.listOfPUModuleReview}/${id}`;
                        updateApiPath = `${apiPaths.listOfPUModuleReview}/${id}`;
                        updatedElement = {
                            rating,
                            review,
                            noOfLikes,
                            noOfDislikes,
                            isInappropriate: false
                        };
                        break;
                    case "forumPosts":
                        apiPath = `${apiPaths.listOfForumPosts}/${id}`;
                        updateApiPath = `${apiPaths.listOfForumPosts}/editForumPostByAdmin/${id}`;
                        updatedElement = {
                            title,
                            message,
                            noOfLikes,
                            noOfDislikes,
                            isInappropriate: false
                        };
                        break;
                    default:
                        break;
                }

                if (selectValue === "true") {
                    await API.delete(apiPath);
                    setModalTitle("Successful Approval of Removal of Content");
                    setModalDescription("You have approved of removing the content successfully");
                } else {
                    await API.put(updateApiPath, updatedElement);
                    setModalTitle("Successful Disapproval of Removal of Content");
                    setModalDescription("You have disapproved of removing the content successfully");
                }

                setShowModal(true);
            } catch (error) {
                console.error(error);
            }
        }
    };

    const handleCancel = async () => {
        setShowModal(false);
        navigate(`../admin/systemSupportAdmin/inappropriatenessContent`);
    };

    const handleCancelNotFoundModal = () => {
        setShowNotFoundModal(false);
        navigate('../admin/systemSupportAdmin/inappropriatenessContent');
    };

    const validate = () => {
        let isValid = true;
        if (selectValue === "") {
            setApprovalError("Please select an option");
            isValid = false;
        } else {
            setApprovalError("");
        }

        return isValid;
    }

    useEffect(() => {
        const fetchData = async () => {
            try {
                let apiPath = "";
                let message = "";
                let review = "";
                let rating = -1;
                let topicName = "";
                let title = "";
                let studentName = "";
                let noOfLikes = 0;
                let noOfDislikes = 0;

                switch (typeOfComponent) {
                    case "forumComments":
                        apiPath = `${apiPaths.listOfForumComments}/${id}`;
                        break;
                    case "puReviews":
                        apiPath = `${apiPaths.listOfPUReviews}/query?id=${id}`;
                        break;
                    case "forumTopics":
                        apiPath = `${apiPaths.listOfForumTopics}/${id}`;
                        break;
                    case "puModuleReviews":
                        apiPath = `${apiPaths.listOfPUModuleReview}/${id}`;
                        break;
                    case "forumPosts":
                        apiPath = `${apiPaths.listOfForumPosts}/${id}`;
                        break;
                    default:
                        break;
                }

                const response = await API.get(apiPath);
                const data = response.data;

                if (data.stringStatus === "404") {
                    setShowNotFoundModal(true);
                    return;
                }

                if (!data.isInappropriate) {
                    setShowAppropriateModal(true);
                    return;
                }

                switch (typeOfComponent) {
                    case "forumComments":
                        message = data.message;
                        setMessage(message);
                        setLabelOfText("Message");
                        setHeaderMessage("Forum Comment");
                        setOutputOfText(message);
                        break;
                    case "puReviews":
                    case "puModuleReviews":
                        review = data.review;
                        rating = data.rating;
                        setReview(review);
                        setRating(rating);
                        setLabelOfText("Review");
                        setOutputOfText(review);

                        if (typeOfComponent === "puReviews") {
                            setHeaderMessage("PU Review");
                        } else {
                            setHeaderMessage("PU Module Review");
                        }
                        break;
                    case "forumTopics":
                        topicName = data.topicName;
                        setTopicName(topicName);
                        setLabelOfText("Topic Name");
                        setHeaderMessage("Forum Topic");
                        setOutputOfText(topicName);
                        break;
                    case "forumPosts":
                        message = data.message;
                        title = data.title;
                        setMessage(message);
                        setTitle(title);
                        setLabelOfText("Title");
                        setHeaderMessage("Forum Post");
                        setOutputOfText(message);
                        break;
                    default:
                        break;
                }

                if (typeOfComponent !== "forumTopics") {
                    noOfLikes = data.noOfLikes;
                    noOfDislikes = data.noOfDislikes;
                    setNoOfLikes(noOfLikes);
                    setNoOfDislikes(noOfDislikes);
                }

                studentName = data.studentFirstName + " " + data.studentLastName;
                setStudentName(studentName);
            } catch (error) {
                console.error(error);
            }
        };

        fetchData();
    }, [id, typeOfComponent]);

    return (
        <div>
            <Header />
            <Menu />
            <div className="content-wrapper">
                <div className="card">
                    <div className="card card-primary">
                        <div className="card-header">
                            <h3 className="card-title">View {headerMessage} Details</h3>
                        </div>
                        <div className="card-body">
                            {typeOfComponent === "forumPosts" && (
                                <div className="form-group">
                                    <label htmlFor="inputReview">Title</label>
                                    <textarea id="inputReview" className="form-control" rows={4} value={title} readOnly />
                                </div>
                            )}
                            <div className="form-group">
                                <label htmlFor="inputReview">{labelOfText}</label>
                                <textarea id="inputReview" className="form-control" rows={4} value={outputOfText} readOnly />
                            </div>
                            <div className="form-group">
                                <label htmlFor="inputStudent">Created By</label>
                                <input type="text" id="inputStudent" className="form-control" value={studentName} readOnly />
                            </div>
                            <div className="form-group">
                                <label htmlFor="inputName">Approved of Inappropriate Content</label>
                                <select className={`custom-select rounded-0 form-control ${approvalError ? "is-invalid" : ""}`} id="approvalSelectOption" value={selectValue} onChange={(e) => setSelectValue(e.target.value)}>
                                    <option value="">Select Option</option>
                                    <option value={Boolean(true)}>Yes</option>
                                    <option value={Boolean(false)}>No</option>
                                </select>
                                {approvalError && <div className="invalid-feedback">{approvalError}</div>}
                            </div>
                            <div className="text-center">
                                <button className="btn btn-success mr-2" onClick={handleResponse}>Submit</button>
                            </div>
                        </div>
                        {showModal && (
                            <div className="modal fade show" id="modal-default" style={{ display: "block" }}>
                                <div className="modal-dialog">
                                    <div className="modal-content">
                                        <div className="modal-header">
                                            <h4 className="modal-title">{modalTitle}</h4>
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
                                            <p>{modalDescription}</p>
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
                        {showNotFoundModal && (
                            <div className="modal fade show" id="modal-default" style={{ display: "block" }}>
                                <div className="modal-dialog">
                                    <div className="modal-content">
                                        <div className="modal-header">
                                            <h4 className="modal-title">Resource Not Found</h4>
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
                                            <p>The requested resource could not be found!</p>
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
                         {showAppropriateModal && (
                            <div className="modal fade show" id="modal-default" style={{ display: "block" }}>
                                <div className="modal-dialog">
                                    <div className="modal-content">
                                        <div className="modal-header">
                                            <h4 className="modal-title">Appropriate Resource</h4>
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
                                            <p>The requested resource is appropriate and not inappropriate!</p>
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
                    </div>
                </div>
            </div>
            <Footer />
        </div>
    );
};

export default InappropriatenessDetails;
