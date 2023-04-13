import React from 'react';
import { useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';

import {
    Card,
    CardBody,
    CardHeader,
    FormText,
    Form,
    Col,
    Label,
    FormGroup,
    Input,
    Button
} from 'reactstrap';

import NavbarComp from './components/NavbarComp';
import { AuthProvider, useAuth } from './login/AuthContext';

export default function NewPost() {
    const { id, topicName } = useParams();
    const [title, setTitle] = useState('');
    const [content, setContent] = useState('');

    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [user, setUser] = useState(null);
    const { loggedInStudent, login, logout } = useAuth();

    const handleTitleChange = (e) => {
        setTitle(e.target.value);
    };

    const handleContentChange = (e) => {
        setContent(e.target.value);
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        //const formData = new FormData();

        //formData.append('title', title);
        //formData.append('content', content);

        const createdForumPost = {
            title,
            content
        };

        axios.post(`http://localhost:8080/PU-war/webresources/forumPosts/user/forumTopics/${id}/student/1/forumPosts`, createdForumPost)
            .then((response) => {
                console.log(response.data);
            })
            .catch((error) => {
                console.error(error);
            });
    };

    return (
        <div className="wrapper" >
            <NavbarComp isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn} user={user} />
            <Card>
                <CardHeader>Create a new post for topic: {topicName}</CardHeader>
                <CardBody>
                    <Form onSubmit={handleSubmit}>
                        <FormGroup row>
                            <Label for="postTitle" sm={2}>
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
                            <Label for="postContent" sm={2}>
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
                            <Col sm={{ size: 10, offset: 2 }}>
                                <div className="text-right">
                                    <Button color="outline-primary" type="submit">
                                        Create post
                                    </Button>
                                </div>
                            </Col>
                        </FormGroup>
                    </Form>
                </CardBody>
            </Card>
        </div>
    );
}