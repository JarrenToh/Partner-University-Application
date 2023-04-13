import React from 'react';
import { useState, useContext, useEffect } from 'react';
import { useParams, Link, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { AuthContext } from "./login/AuthContext";

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

export default function NewPost() {
    const { loggedInStudent } = useContext(AuthContext);
    const [studentId, setStudentId] = useState(0);
    const { id, topicName } = useParams();
    const [title, setTitle] = useState("");
    const [content, setContent] = useState("");
    const [ saveButtonDisabled, setSaveButtonDisabled ] = useState(true);
    const [alertVisible, setAlertVisible] = useState(false);
    const [alertMessage, setAlertMessage] = useState('');
    const [alertType, setAlertType] = useState('danger');
    const navigate = useNavigate();

    useEffect(() => {
        if (loggedInStudent) {
          setStudentId(loggedInStudent.studentId);
        }
    }, [loggedInStudent]);

    const handleTitleChange = (e) => {
        setTitle(e.target.value);
    };

    const handleContentChange = (e) => {
        setContent(e.target.value);
    };

    useEffect(() => {
        function checkButton() {
            if (title.trim().length === 0 || content.trim().length === 0) {
                setSaveButtonDisabled(true);
            } else {
                setSaveButtonDisabled(false);
            }
        }
      
        checkButton();
      }, [title, content]);

    if (!loggedInStudent) {
      return <h1 style={{ textAlign: 'center', color: 'red', margin: '0 auto', width: '50%', fontWeight: 'bold', fontSize: '2em'}}>You are not logged in.</h1>;
    }

    const handleSubmit = (e) => {
        e.preventDefault();

        const createdForumPost = {
            title,
            content
        };

        axios.post(`http://localhost:8080/PU-war/webresources/forumPosts/user/forumTopics/${id}/student/${studentId}/forumPosts`, createdForumPost)
            .then((response) => {
                console.log(response.data);
                setAlertType('success');
                setAlertMessage('The post was created successfully!');
                setAlertVisible(true);
        
                setTimeout(() => {
                    navigate(`/forum-topics/${id}/${topicName}`);
                }, 1000);
            })
            .catch((error) => {
                console.error(error);
                setAlertType('danger');
                setAlertMessage('Error creating the post. Please try again.');
                setAlertVisible(true);
            });
    };

    return (
        <div>
        <Card>
            <CardHeader>Create a new post for topic: {topicName}</CardHeader>
            <CardBody>
                <Form onSubmit={handleSubmit}>
                    <FormGroup row>
                        <Label for="postTitle" sm={1}>
                            Title
                        </Label>
                        <Col sm={10}>
                            <Input
                                type="text"
                                name="title"
                                id="postTitle"
                                placeholder="Enter a title"
                                value={title}
                                onChange={handleTitleChange}
                            />
                        </Col>
                    </FormGroup>
                    <FormGroup row>
                        <Label for="postContent" sm={1}>
                            Content
                        </Label>
                        <Col sm={10}>
                            <Input
                                type="textarea"
                                name="content"
                                id="postContent"
                                value={content}
                                onChange={handleContentChange}
                            />
                        </Col>
                    </FormGroup>
                    <FormGroup row>
                        <Col sm={{ size: 10, offset: 1 }}>
                            <div className="text-right">
                            <Button variant="success"
                                    disabled={saveButtonDisabled} type="submit">
                                    Create post
                                </Button>
                                <Button variant="outline-danger" tag={Link}
                                    to={`/forum-topics/${id}/${topicName}`}>
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