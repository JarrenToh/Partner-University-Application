import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";

import Header from "../../../components/dashboard/Header";
import Menu from "../../../components/dashboard/Menu";
import Footer from "../../../components/dashboard/Footer";

import API from "../../../../util/API";
import apiPaths from "../../../../util/apiPaths";

const ForumCommentDetails = () => {
    const { id } = useParams();
    const [isChecked, setIsChecked] = useState(false);
    const [message, setMessage] = useState("");
    const [noOfDislikes, setNoOfDislikes] = useState(undefined);
    const [noOfLikes, setNoOfLikes] = useState(undefined);
    const [studentName, setStudentName] = useState("");

    const handleResponse = async () => {
        try {
            const apiPath = `${apiPaths.listOfForumComments}/${id}`;
            const updateApiPath = `${apiPaths.listOfForumComments}/editForumCommentByAdmin/${id}`;

            if (isChecked) { // delete the post
                await API.delete(apiPath);
                alert("You have approved of removing the content successfully");
                return;
            } // update the post back appropriate 

            const updatedForumComment = {
                message,
                noOfLikes,
                noOfDislikes,
                isInappropriate: false
            };

            await API.put(updateApiPath, updatedForumComment);
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
                const apiPath = `${apiPaths.listOfForumComments}/${id}`
                const response = await API.get(apiPath);
                const data = response.data;

                if (!data.isInappropriate) {
                    alert("This review is appropriate. fk off");
                    return;
                }

                const message = data.message;
                const noOfLikes = data.noOfLikes;
                const noOfDislikes = data.noOfDislikes;
                const studentName = data.studentFirstName + " " + data.studentLastName;

                setMessage(message);
                setNoOfLikes(noOfLikes);
                setNoOfDislikes(noOfDislikes);
                setStudentName(studentName);
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
                            <h3 className="card-title">View Forum Comment Details</h3>
                        </div>
                        <div className="card-body">
                            <div className="form-group">
                                <label htmlFor="inputReview">Message</label>
                                <textarea id="inputReview" className="form-control" rows={4} value={message} readOnly />
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

export default ForumCommentDetails;
