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

export default function EditComment() {
    const { loggedInStudent } = useContext(AuthContext);
    const { commentId, oldCommentMessage, postId, topicName, topicId } = useParams();
    const [ commentMessage, setCommentMessage ] = useState(oldCommentMessage);
    const [ saveButtonDisabled, setSaveButtonDisabled ] = useState(true);
    const [alertVisible, setAlertVisible] = useState(false);
    const [alertMessage, setAlertMessage] = useState('');
    const [alertType, setAlertType] = useState('danger');
    const navigate = useNavigate();

    if (!loggedInStudent) {
        return <h1 style={{ textAlign: 'center', color: 'red', margin: '0 auto', width: '50%', fontWeight: 'bold', fontSize: '2em'}}>You are not logged in.</h1>;
    }

    const handleCommentMessageChange = (e) => {
        const newCommentMessage = e.target.value;
      
        if (newCommentMessage.trim() !== oldCommentMessage.trim()) {
          setSaveButtonDisabled(false);
        } else {
          setSaveButtonDisabled(true);
        }
      
        setCommentMessage(newCommentMessage);
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        const createdForumComment = {
            message: commentMessage
        };

        axios.put(`http://localhost:8080/PU-war/webresources/forumComments/${commentId}`, createdForumComment)
            .then((response) => {
                console.log(response.data);
                setAlertType('success');
                setAlertMessage('The comment was edited successfully!');
                setAlertVisible(true);
        
                setTimeout(() => {
                    navigate(`/view-post/${postId}/${topicName}/${topicId}`);
                }, 1000);
            })
            .catch((error) => {
                console.error(error);
                setAlertType('danger');
                setAlertMessage('Error editing the comment. Please try again.');
                setAlertVisible(true);
            });
    };

    return (
        <div>
        <Card>
            <CardHeader>Edit comment</CardHeader>
            <CardBody>
                <Form onSubmit={handleSubmit}>
                    <FormGroup row>
                        <Label for="message" sm={2}>
                            Message
                        </Label>
                        <Col sm={10}>
                            <Input
                                type="text"
                                name="message"
                                id="message"
                                value={commentMessage}
                                onChange={handleCommentMessageChange}
                            />
                        </Col>
                    </FormGroup>
                    <FormGroup row>
                        <Col sm={{ size: 10, offset: 2 }}>
                            <div className="text-right">
                                <Button variant="success"
                                    disabled={saveButtonDisabled} type="submit">
                                    Edit comment
                                </Button>
                                <Button variant="outline-danger" tag={Link}
                                    to={`/view-post/${postId}/${topicName}/${topicId}`}>
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