import React from 'react';
import { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';

import {
    Card,
    CardBody,
    CardHeader,
    Form,
    Label,
    FormGroup,
    Input,
    Button
} from 'reactstrap';

export default function NewTopic() {
    const { studentId, puId } = useParams();
    const [pu, setPu] = useState({});
    const [topicName, setTopicName] = useState("");

    useEffect(() => {
        const fetchData = async () => {
          try {
            const response = await axios.get(`http://localhost:8080/PU-war/webresources/pu/getPUById/${puId}`);
            setPu(response.data);
          } catch (error) {
            console.error(error);
          }
        };
        fetchData();
    }, []);

    const handleTopicNameChange = (e) => {
        setTopicName(e.target.value);
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        const createdForumTopic = {
            topicName,
            puId: puId
        };

        axios.post(`http://localhost:8080/PU-war/webresources/forumTopics/user/student/${studentId}`, createdForumTopic)
            .then((response) => {
                console.log(response.data);
            })
            .catch((error) => {
                console.error(error);
            });
    };

    return (
        <Card>
            <CardHeader>Create a new topic for {pu.name}</CardHeader>
            <CardBody>
                <Form onSubmit={handleSubmit}>
                    <div className="row">
                        <FormGroup>
                            <Label for="topicName">Topic Name</Label>
                        </FormGroup>
                        <FormGroup>
                            <Input
                                type="text"
                                name="topicName"
                                id="topicName"
                                placeholder="Enter a topic name"
                                value={topicName}
                                onChange={handleTopicNameChange}
                            />
                        </FormGroup>
                        <FormGroup>
                            <div className="text-right">
                                <Button color="outline-primary" type="submit">
                                    Create topic
                                </Button>
                            </div>
                        </FormGroup>
                    </div>
                </Form>
            </CardBody>
        </Card>
    );
}