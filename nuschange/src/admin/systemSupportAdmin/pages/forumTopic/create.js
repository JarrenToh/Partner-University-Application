import React, { useState } from 'react';

import Header from '../../../components/dashboard/Header';
import Menu from '../../../components/dashboard/Menu';
import Footer from '../../../components/dashboard/Footer';

import API from '../../../../util/API';
import apiPaths from '../../../../util/apiPaths';

const ForumTopic = () => {
    const [topicName, setTopicName] = useState("");

    const handleCreate = async () => {
        try {
            const createForumTopic = {
                topicName
            };

            // TODO: Change to get the adminId dynamically
            const apiPath = `${apiPaths.listOfAdminForumTopics}?adminId=${1}`;
            await API.post(apiPath, createForumTopic);

            alert("Forum Topic has been created successfully");
        } catch (error) {
            console.error(error);
        }
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
                                <input type="text" id="inputName" className="form-control" placeholder="Input a topic Name" onChange={(e) => setTopicName(e.target.value)} />
                            </div>
                            <div className="text-center">
                                <button className="btn btn-success mr-2" onClick={handleCreate}>Create</button>
                            </div>
                        </div>
                        <br />
                    </div>
                </div>
            </div>
            <Footer />
        </div>
    )
};

export default ForumTopic;