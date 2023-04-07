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

export default function EditPost() {
    const { id, oldTitle, oldMessage, topicName} = useParams();
    const [title, setTitle] = useState(oldTitle);
    const [content, setContent] = useState(oldMessage);

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

        axios.put(`http://localhost:8080/PU-war/webresources/forumPosts/${id}`, createdForumPost)
            .then((response) => {
                console.log(response.data);
            })
            .catch((error) => {
                console.error(error);
            });
    };

    return (
        <Card>
            <CardHeader>Edit post for topic: {topicName}</CardHeader>
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
                                    Edit post
                                </Button>
                            </div>
                        </Col>
                    </FormGroup>
                </Form>
            </CardBody>
        </Card>
    );
}