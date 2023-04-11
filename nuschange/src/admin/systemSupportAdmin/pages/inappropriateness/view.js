import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";

import Header from "../../../components/dashboard/Header";
import Menu from "../../../components/dashboard/Menu";
import Footer from "../../../components/dashboard/Footer";

import API from "../../../../util/API";
import apiPaths from "../../../../util/apiPaths";

const InappropriatenessDetails = () => {
    const { typeOfComponent, id } = useParams();
    const [isChecked, setIsChecked] = useState(false);

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

    const handleResponse = async () => {
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

            if (isChecked) { // delete the post
                await API.delete(apiPath);
                alert("You have approved of removing the content successfully");
                return;
            } // update the post back appropriate 

            await API.put(updateApiPath, updatedElement);
            alert("You have disapproved of removing the content successfully");
        } catch (error) {
            console.error(error);
        }
    };

    const handleCheckboxChange = (event) => {
        setIsChecked(event.target.checked);
    };

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

                if (!data.isInappropriate) {
                    alert("This review is appropriate.");
                    // TODO: navigate to where it belong
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
                            <div className="form-check">
                                <input
                                    className="form-check-input"
                                    type="checkbox"
                                    checked={isChecked}
                                    onChange={handleCheckboxChange}
                                />
                                <label className="form-check-label">Approved of removing</label>
                            </div>
                            <div className="text-center">
                                <button className="btn btn-success mr-2" onClick={handleResponse}>Submit</button>
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

export default InappropriatenessDetails;
