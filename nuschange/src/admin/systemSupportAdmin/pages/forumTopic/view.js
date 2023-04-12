import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";

import Header from "../../../components/dashboard/Header";
import Menu from "../../../components/dashboard/Menu";
import Footer from "../../../components/dashboard/Footer";

import API from "../../../../util/API";
import apiPaths from "../../../../util/apiPaths";

const ForumTopicDetails = () => {
    const { id } = useParams();
    const [topicName, setTopicName] = useState("");
    const [isInappropriate, setIsInappropriate] = useState(false);

    const handleEdit = async () => {
        try {
            const updatedForumTopic = {
                topicName,
                isInappropriate
            };

            // TODO: Change to get the adminId dynamically
            const apiPath = `${apiPaths.listOfForumTopics}/editForumTopicByAdmin/${id}?adminId=1`;
            await API.put(apiPath, updatedForumTopic);

            alert("Forum Topic has been updated successfully");
        } catch (error) {
            console.error(error);
        }
    };

    const handleDelete = async () => {
        try {
            const apiPath = `${apiPaths.listOfForumTopics}/${id}`;
            await API.delete(apiPath);
            alert("Forum Topic has been deleted successfully!");
            // TODO: Redirect to FAQs list page
        } catch (error) {
            console.error(error);
        }
    };

    useEffect(() => {
        const fetchData = async () => {
            try {
                const apiPath = `${apiPaths.listOfForumTopics}/${id}`
                const response = await API.get(apiPath);
                const data = response.data;

                const topicName = data.topicName;
                const isInappropriate = data.isInappropriate;

                setTopicName(topicName);
                setIsInappropriate(isInappropriate);
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
                            <h3 className="card-title">View Forum Topic Details</h3>
                        </div>
                        <div className="card-body">
                            <div className="form-group">
                                <label htmlFor="inputName">Topic Name</label>
                                <input type="text" id="inputName" className="form-control" value={topicName} onChange={(e) => setTopicName(e.target.value)} />
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
            <Footer />
        </div>
    );
};

export default ForumTopicDetails;
