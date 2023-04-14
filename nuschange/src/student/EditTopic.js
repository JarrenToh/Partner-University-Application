import React from 'react';
import { useState, useContext, useEffect } from 'react';
import { useParams, Link, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { AuthContext } from '../../src/AuthContext';


import {
    Card,
    CardBody,
    CardHeader,
    Form,
    Col,
    Label,
    FormGroup,
    Input,
    Button,
    Alert
} from 'reactstrap';

export default function EditTopic() {
    const { loggedInStudent } = useContext(AuthContext);
    const { topicId, oldTopicName } = useParams();
    const [ topicName, setTopicName ] = useState(oldTopicName);
    const [ saveButtonDisabled, setSaveButtonDisabled ] = useState(true);
    const [alertVisible, setAlertVisible] = useState(false);
    const [alertMessage, setAlertMessage] = useState('');
    const [alertType, setAlertType] = useState('danger');
    const navigate = useNavigate();

    if (!loggedInStudent) {
      return <h1 style={{ textAlign: 'center', color: 'red', margin: '0 auto', width: '50%', fontWeight: 'bold', fontSize: '2em'}}>You are not logged in.</h1>;
    }

    const handleTopicNameChange = (e) => {
        const newTopicName = e.target.value;
      
        if (newTopicName.trim() !== oldTopicName.trim()) {
          setSaveButtonDisabled(false);
        } else {
          setSaveButtonDisabled(true);
        }
      
        setTopicName(newTopicName);
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        const createdForumTopic = {
            topicName
        };

        axios.put(`http://localhost:8080/PU-war/webresources/forumTopics/${topicId}`, createdForumTopic)
            .then((response) => {
                console.log(response.data);
                setAlertType('success');
                setAlertMessage('The topic was edited successfully!');
                setAlertVisible(true);
        
                setTimeout(() => {
                    navigate(`/student/forum-topics/0`);
                }, 1000);
            })
            .catch((error) => {
                console.error(error);
                setAlertType('danger');
                setAlertMessage('Error editing the topic. Please try again.');
                setAlertVisible(true);
            });
    };

    return (
        <div>
        <Card>
            <CardHeader>Edit topic</CardHeader>
            <CardBody>
                <Form onSubmit={handleSubmit}>
                    <FormGroup row>
                        <Label for="topicName" sm={2}>
                            Topic Name
                        </Label>
                        <Col sm={10}>
                            <Input
                                type="text"
                                name="topicName"
                                id="topicName"
                                value={topicName}
                                onChange={handleTopicNameChange}
                            />
                        </Col>
                    </FormGroup>
                    <FormGroup row>
                        <Col sm={{ size: 10, offset: 2 }}>
                            <div className="text-right">
                                <Button variant="success"
                                    disabled={saveButtonDisabled} type="submit">
                                    Edit topic
                                </Button>
                                <Button variant="outline-danger" tag={Link}
                                    to={`/student/forum-topics/0`}>
                                    Close
                                </Button>
                            </div>
                        </Col>
                    </FormGroup>
                </Form>
            </CardBody>
        </Card>
        {alertVisible && (
                <Alert color={alertType} toggle={() => setAlertVisible(false)}>
                    {alertMessage}
                </Alert>
            )}
        </div>
    );
}