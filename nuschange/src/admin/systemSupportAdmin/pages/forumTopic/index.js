import React, { useState, useEffect } from "react";
import { useNavigate } from 'react-router-dom';

import Header from "../../../components/dashboard/Header";
import Menu from "../../../components/dashboard/Menu";
import Footer from "../../../components/dashboard/Footer";

import API from "../../../../util/API";
import apiPaths from "../../../../util/apiPaths";
import { DateTimeConverter } from "../../../../util/dateTimeConverter";

const ForumTopic = () => {

    const [data, setData] = useState([]);
    const [showAll, setShowAll] = useState(true);
    const navigate = useNavigate();

    const handleButtonClick = (forumTopicId) => {
        navigate(`/forumTopics/${forumTopicId}`);
    };

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await API.get(apiPaths.listOfForumTopics);
                let filteredData = "";
                if (showAll) {
                    filteredData = response.data.filter(item => item.admin !== undefined);
                    setData(filteredData);
                } else {
                    // TODO: need to change this dyanmically later
                    filteredData = response.data.filter(item => item.admin !== undefined && item.admin.adminId === 1); // assume you have the current user's id stored somewhere
                    setData(filteredData);
                }
            } catch (error) {
                console.error(error);
            }
        };
        fetchData();
    }, [data, showAll]);

    const handleToggle = () => {
        setShowAll(!showAll);
    };

    return (
        <div>
            <Header />
            <Menu />
            <div className="content-wrapper">
                <div className="card">
                    <div className="card-header">
                        <h3 className="card-title">Forum Topics</h3>
                        <button type="button" className="btn btn-block btn-outline-dark" onClick={handleToggle}>{showAll ? 'Show only my Forum Topics' : 'Show all Forum Topics'}</button>
                    </div>
                    <div className="card-body">
                        <table id="example1" className="table table-bordered table-striped">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Topic Name</th>
                                    <th>Created By</th>
                                    <th>Created At</th>
                                    <th>Last Edit At</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                {data.length > 0 ? (
                                    data.map((item, index) => item.admin !== undefined && (
                                        <tr key={index}>
                                            <td>{item.topicId}</td>
                                            <td>{item.topicName}</td>
                                            <td>{item.admin.name}</td>
                                            <td>{DateTimeConverter.convertDateForNicerOutput(item.timeOfCreation)}</td>
                                            <td>{item.lastEdit === undefined ? "-" : DateTimeConverter.convertDateForNicerOutput(item.lastEdit)}</td>
                                            <td>
                                                <button onClick={() => handleButtonClick(item.topicId)} type="button" className="btn btn-primary">View Details</button>
                                            </td>
                                        </tr>
                                    )
                                    ))
                                    : (
                                        <tr>
                                            <td colSpan={6} style={{ textAlign: "center" }}>No data available</td>
                                        </tr>
                                    )}
                            </tbody>
                            <tfoot>
                                <tr>
                                    <th>ID</th>
                                    <th>Topic Name</th>
                                    <th>Created By</th>
                                    <th>Created At</th>
                                    <th>Last Edit At</th>
                                    <th>Actions</th>
                                </tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
            <Footer />
        </div>
    )
};

export default ForumTopic;
