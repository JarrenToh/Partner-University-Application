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
    const { studentId } = useParams();
    const [pus, setPus] = useState([]);
    const [topicName, setTopicName] = useState("");
    const [selectedPuId, setSelectedPuId] = useState(1);

    useEffect(() => {
        const fetchData = async () => {
          try {
            const response = await axios.get(`http://localhost:8080/PU-war/webresources/pu`);
            setPus(response.data);
          } catch (error) {
            console.error(error);
          }
        };
        fetchData();
    }, []);

    const handleTopicNameChange = (e) => {
        setTopicName(e.target.value);
    };

    const handlePuSelectChange = (e, puId) => {
        e.preventDefault();
        setSelectedPuId(puId);
    }

    const handleSubmit = (e) => {
        e.preventDefault();

        const createdForumTopic = {
            topicName,
            puId: selectedPuId
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
            <CardHeader>Create a new topic</CardHeader>
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
                            <Label for="puSelect">Select Partner University</Label>
                            <Input
                                type="select"
                                name="select"
                                id="puSelect"
                                value={selectedPuId}
                                onChange={(e) => handlePuSelectChange(e, e.target.value)}
                            >
                                {pus.map(pu => (
                                    <option value={pu.puId} key={pu.puId}>{pu.name}</option>
                                ))}
                            </Input>
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