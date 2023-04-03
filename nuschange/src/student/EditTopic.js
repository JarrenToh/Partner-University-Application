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

export default function EditTopic() {
    const { topicId, oldTopicName } = useParams();
    const [topicName, setTopicName] = useState(oldTopicName);

    const handleTopicNameChange = (e) => {
        setTopicName(e.target.value);
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        //const formData = new FormData();

        //formData.append('title', title);
        //formData.append('content', content);

        const createdForumTopic = {
            topicName
        };

        axios.put(`http://localhost:8080/PU-war/webresources/forumTopics/${topicId}`, createdForumTopic)
            .then((response) => {
                console.log(response.data);
            })
            .catch((error) => {
                console.error(error);
            });
    };

    return (
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
                                <Button color="outline-primary" type="submit">
                                    Edit topic
                                </Button>
                            </div>
                        </Col>
                    </FormGroup>
                </Form>
            </CardBody>
        </Card>
    );
}