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

export default function NewTopic() {
    const { studentId } = useParams();
    const [topicName, setTopicName] = useState('');

    const handleTopicNameChange = (e) => {
        setTopicName(e.target.value);
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        //const formData = new FormData();

        //formData.append('title', title);
        //formData.append('content', content);

        const createdForumPost = {
            topicName
        };

        axios.post(`http://localhost:8080/PU-war/webresources/forumTopics/user/student/${studentId}`, createdForumPost)
            .then((response) => {
                console.log(response.data);
            })
            .catch((error) => {
                console.error(error);
            });
    };

    return (
        <Card>
            <CardHeader>Create a new topic</CardHeader>
            <CardBody>
                <Form onSubmit={handleSubmit}>
                    <FormGroup>
                        <Label for="topicName" sm={1}>
                            Topic Name
                        </Label>
                        <Col sm={10}>
                            <Input
                                type="text"
                                name="topicName"
                                id="topicName"
                                placeholder="Enter a topic name"
                                value={topicName}
                                onChange={handleTopicNameChange}
                            />
                        </Col>
                    </FormGroup>
                    <FormGroup row>
                        <Col sm={{ size: 10, offset: 2 }}>
                            <div className="text-right">
                                <Button color="outline-primary" type="submit">
                                    Create topic
                                </Button>
                            </div>
                        </Col>
                    </FormGroup>
                </Form>
            </CardBody>
        </Card>
    );
}