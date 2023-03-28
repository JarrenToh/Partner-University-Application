import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';

import Footer from '../../../components/dashboard/Footer';
import Header from '../../../components/dashboard/Header';
import Menu from '../../../components/dashboard/Menu';

import API from '../../../../util/API';
import apiPaths from '../../../../util/apiPaths';

const EnquiryDetails = () => {

    const { id } = useParams();
    const [title, setTitle] = useState("");
    const [content, setContent] = useState("");
    const [reply, setReply] = useState("");

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
                reply
            };

            // TODO: Change to get the adminId dynamically
            const apiPath = `${apiPaths.listOfEnquiries}/${id}/respond?adminId=1`;
            await API.put(apiPath, updatedEnquiry);

            alert("Enquiry has been responded successfully");
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
                            <h3 className="card-title">View Enquiry Details</h3>
                        </div>
                        <div className="card-body">
                            <div className="form-group">
                                <label htmlFor="inputName">Title</label>
                                <p>{title}</p>
                            </div>
                            <div className="form-group">
                                <label htmlFor="inputDescription">Content</label>
                                <p>{content}</p>
                            </div>
                            <div className="form-group">
                                <label htmlFor="inputDescription">Response</label>
                                <textarea id="inputResponse" className="form-control" rows={4} placeholder="Input a response" onChange={(e) => setReply(e.target.value)} />
                            </div>
                            <div className="text-center">
                                <button className="btn btn-success mr-2" onClick={handleUpdate}>Submit</button>
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

export default EnquiryDetails;