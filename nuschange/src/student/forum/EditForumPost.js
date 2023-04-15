import React from 'react';
import { useState, useContext, useEffect } from 'react';
import { useParams, Link, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { AuthContext } from '../../../src/AuthContext';
import NavbarComp from '../../student/components/NavbarComp';
import NotLoggedIn from '../../student/components/NotLoggedInPage';
import { Helmet } from "react-helmet";

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

export default function EditPost() {
  const { loggedInStudent } = useContext(AuthContext);
  const { topicId, id, oldTitle, oldMessage, topicName, number } = useParams();
  const [title, setTitle] = useState(oldTitle);
  const [content, setContent] = useState(oldMessage);
  const [saveButtonDisabled, setSaveButtonDisabled] = useState(true);
  const [alertVisible, setAlertVisible] = useState(false);
  const [alertMessage, setAlertMessage] = useState('');
  const [alertType, setAlertType] = useState('danger');
  const navigate = useNavigate();

  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [user, setUser] = useState(null);

  const handleTitleChange = (e) => {
    setTitle(e.target.value);
  };

  const handleContentChange = (e) => {
    setContent(e.target.value);
  };

  useEffect(() => {
    function checkButton() {
      if (title.trim() === oldTitle.trim() && content.trim() === oldMessage.trim()) {
        setSaveButtonDisabled(true);
      } else {
        setSaveButtonDisabled(false);
      }
    }
    checkButton();
  }, [title, content]);

  if (!loggedInStudent) {
    return NotLoggedIn();
  }

  const handleSubmit = (e) => {
    e.preventDefault();

    const createdForumPost = {
      title,
      content
    };

    axios.put(`http://localhost:8080/PU-war/webresources/forumPosts/${id}`, createdForumPost)
      .then((response) => {
        console.log(response.data);
        setAlertType('success');
        setAlertMessage('The post was edited successfully!');
        setAlertVisible(true);

        setTimeout(() => {
          if (number == 0) {
            navigate(`/student/forum-topics/${topicId}/${topicName}`);
          } else if (number == 1) {
            navigate(`/student/view-post/${id}/${topicName}/${topicId}`);
          }
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
      <div>
        <Helmet>
          <title>Edit Forum Topic</title>
        </Helmet>
      </div>
<NavbarComp isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn} user={user} />
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
                  <Button variant="success" style={{marginRight: "10px"}}
                    disabled={saveButtonDisabled} type="submit">
                    Edit post
                  </Button>
                  {number == 0 ?
                    (<Button variant="outline-danger" tag={Link} 
                      to={`/student/forum-topics/${topicId}/${topicName}`}>
                      Close
                    </Button>) : (
                      (<Button variant="outline-danger" tag={Link}
                        to={`/student/view-post/${id}/${topicName}/${topicId}`}>
                        Close
                      </Button>)
                    )
                  }

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