import React, { useState } from 'react';

import Header from '../../../components/dashboard/Header';
import Menu from '../../../components/dashboard/Menu';
import Footer from '../../../components/dashboard/Footer';

import API from '../../../../util/API';
import apiPaths from '../../../../util/apiPaths';

const FAQ = () => {
    const [question, setQuestion] = useState("");
    const [answer, setAnswer] = useState("");

    const handleCreate = async () => {
        try {
            const createdFAQ = {
                question,
                answer
            };

            // TODO: Change to get the adminId dynamically
            const apiPath = `${apiPaths.listOfFaqs}?adminId=1`;
            await API.post(apiPath, createdFAQ);

            alert("FAQ has been created successfully");
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
                            <h3 className="card-title">Create FAQ</h3>
                        </div>
                        <div className="card-body">
                            <div className="form-group">
                                <label htmlFor="inputName">Question</label>
                                <input type="text" id="inputName" className="form-control" placeholder="Input a question" onChange={(e) => setQuestion(e.target.value)} />
                            </div>
                            <div className="form-group">
                                <label htmlFor="inputDescription">Answer</label>
                                <textarea id="inputDescription" className="form-control" rows={4} placeholder="Input an answer" onChange={(e) => setAnswer(e.target.value)} />
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

export default FAQ;