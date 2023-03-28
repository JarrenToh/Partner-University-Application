import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";

import Header from "../../../components/dashboard/Header";
import Menu from "../../../components/dashboard/Menu";
import Footer from "../../../components/dashboard/Footer";

import API from "../../../../util/API";
import apiPaths from "../../../../util/apiPaths";

const FAQDetails = () => {
    const { id } = useParams();
    const [question, setQuestion] = useState("");
    const [answer, setAnswer] = useState("");

    const handleEdit = async () => {
        try {
            const updatedFAQ = {
                question,
                answer
            };

            // TODO: Change to get the adminId dynamically
            const apiPath = `${apiPaths.listOfFaqs}/${id}?adminId=1`;
            await API.put(apiPath, updatedFAQ);

            alert("FAQ has been updated successfully");
        } catch (error) {
            console.error(error);
        }
    };

    const handleDelete = async () => {
        try {
            const apiPath = `${apiPaths.listOfFaqs}/${id}`;
            await API.delete(apiPath);
            alert("FAQ has been deleted successfully!");
            // TODO: Redirect to FAQs list page
        } catch (error) {
            console.error(error);
        }
    };

    useEffect(() => {
        const fetchData = async () => {
            try {
                const apiPath = `${apiPaths.listOfFaqs}/${id}`
                const response = await API.get(apiPath);
                const data = response.data;

                const question = data.question;
                const answer = data.answer;

                setQuestion(question);
                setAnswer(answer);
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
                            <h3 className="card-title">View FAQ Details</h3>
                        </div>
                        <div className="card-body">
                            <div className="form-group">
                                <label htmlFor="inputName">Question</label>
                                <input type="text" id="inputName" className="form-control" value={question} onChange={(e) => setQuestion(e.target.value)} />
                            </div>
                            <div className="form-group">
                                <label htmlFor="inputDescription">Answer</label>
                                <textarea id="inputDescription" className="form-control" rows={4} value={answer} onChange={(e) => setAnswer(e.target.value)} />
                            </div>
                            <div className="text-center">
                                <button className="btn btn-success mr-2" onClick={handleEdit}>Save Changes</button>
                                <button className="btn btn-danger" onClick={handleDelete}>Delete</button>
                            </div>
                        </div>
                        {/* /.card-body */}
                        {/* /.card */}
                        <br />
                    </div>
                </div>
            </div>
            <Footer />
        </div>
    );
};

export default FAQDetails;
