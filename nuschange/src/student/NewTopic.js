import React from 'react';
import { useState, useEffect, useContext } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { AuthContext } from '../../src/AuthContext';
import axios from 'axios';
import NavbarComp from '../student/components/NavbarComp';

import {
    Card,
    CardBody,
    CardHeader,
    Form,
    Label,
    FormGroup,
    Input,
    Button,
    Alert
} from 'reactstrap';

export default function NewTopic() {
    const { loggedInStudent } = useContext(AuthContext);
    const [studentId, setStudentId] = useState(loggedInStudent.studentId);
    const [pus, setPus] = useState([]);
    const [topicName, setTopicName] = useState("");
    const [selectedPuId, setSelectedPuId] = useState(1);
    const [ saveButtonDisabled, setSaveButtonDisabled ] = useState(true);
    const [alertVisible, setAlertVisible] = useState(false);
    const [alertMessage, setAlertMessage] = useState('');
    const [alertType, setAlertType] = useState('danger');
    const navigate = useNavigate();

    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [user, setUser] = useState(null);

    useEffect(() => {
        if (loggedInStudent) {
            setStudentId(loggedInStudent.studentId);
        }
    }, [loggedInStudent]);

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

    if (!loggedInStudent) {
      return <h1 style={{ textAlign: 'center', color: 'red', margin: '0 auto', width: '50%', fontWeight: 'bold', fontSize: '2em'}}>You are not logged in.</h1>;
    }

    const handleTopicNameChange = (e) => {
        const newTopicName = e.target.value;
      
        if (newTopicName.trim().length === 0) {
          setSaveButtonDisabled(true);
        } else {
          setSaveButtonDisabled(false);
        }
      
        setTopicName(newTopicName);
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
                setAlertType('success');
                setAlertMessage('The topic was created successfully!');
                setAlertVisible(true);
        
                setTimeout(() => {
                    navigate(`/student/forum-topics/0`);
                }, 1000);
            })
            .catch((error) => {
                console.error(error);
                setAlertType('danger');
                setAlertMessage('Error creating the topic. Please try again.');
                setAlertVisible(true);
            });
    };

    return (
        <div>
            <NavbarComp isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn} user={user} />
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
                                <Button variant="success"
                                    disabled={saveButtonDisabled} type="submit">
                                    Create topic
                                </Button>
                                <Button variant="outline-danger" tag={Link}
                                    to={`/student/forum-topics/0`}>
                                    Close
                                </Button>
                            </div>
                        </FormGroup>
                    </div>
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