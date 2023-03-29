import React from "react";
import { createContext, useContext, useState, useEffect } from "react";
import { AuthContext } from "../login/AuthContext";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faLinkedin,
  faLinkedinIn,
  faTelegram,
  faInstagram,
  faGithub,
} from "@fortawesome/free-brands-svg-icons";

import { faPenToSquare, faPen } from "@fortawesome/free-solid-svg-icons";

import {
  Image,
  Button,
  Col,
  Form,
  Row,
  InputGroup,
  ListGroup,
  ListGroupItem,
  Card,
  Container,
  Accordion,
} from "react-bootstrap";

const StudentProfile = () => {
  const { loggedInStudent, logout } = useContext(AuthContext);
  if (!loggedInStudent) {
    return <div> Not Logged in</div>;
  }
  const studentFullName =
    loggedInStudent.firstName + " " + loggedInStudent.lastName;

  const dummyStudent = {
    studentId: 1,
    firstName: "Muhammad",
    lastName: "Mursyid",
    phoneNumber: "",
    faculty: "School Of Computing",
    socialMedia: [""],
    lastActive: "2023-03-24",
    email: "muhammad_mursyid@u.nus.edu",
    partnerUniversity: "University Of Manchester",
  };

  const dummyStudentFullName =
    dummyStudent.firstName + " " + dummyStudent.lastName;

  console.log(loggedInStudent);

  return (
    <div style={{ paddingLeft: "5%", paddingRight: "5%" }}>
      <div style={{ textAlign: "center", paddingTop: "2.5%" }}>
        <h1>User Profile</h1>
      </div>
      <Row>
        <Col>
          <div id="profilePicDiv" style={{ padding: "5%" }}>
            <Card>
              <Card.Body style={{ padding: "5%", textAlign: "center" }}>
                <Image
                  src={require("../assets/images/avatars/avatar3.jpg")}
                  roundedCircle
                />
              </Card.Body>
              <Card.Footer>
                Last Active: {loggedInStudent.lastActive}
              </Card.Footer>
            </Card>
            <Card>
              <Card.Header as="h5">Social Media</Card.Header>
              <ListGroup variant="flush">
                <ListGroupItem>
                  <FontAwesomeIcon icon={faLinkedin} size="xl" />
                </ListGroupItem>
                <ListGroupItem>
                  <FontAwesomeIcon icon={faTelegram} size="xl" />
                </ListGroupItem>
                <ListGroupItem>
                  <FontAwesomeIcon icon={faInstagram} size="xl" />
                </ListGroupItem>
                <ListGroupItem>
                  <FontAwesomeIcon icon={faGithub} size="xl" />
                </ListGroupItem>
              </ListGroup>
              <Card.Footer style={{ textAlign: "center" }}>
                <Button variant="link">
                  <FontAwesomeIcon icon={faPen} /> {"  "}
                  Edit Social Media Links
                </Button>
              </Card.Footer>
            </Card>
          </div>
        </Col>

        <Col>
          <div id="formDiv" style={{ padding: "5%" }}>
            <Form>
              <Form.Group className="mb-3" controlId="formGroupName">
                <Form.Label>Full Name</Form.Label>
                <Form.Control readOnly defaultValue={studentFullName} />
              </Form.Group>
              <Form.Group className="mb-3" controlId="formGroupEmail">
                <Form.Label>Email</Form.Label>
                <Form.Control readOnly defaultValue={loggedInStudent.email} />
              </Form.Group>
              <Form.Group className="mb-3" controlId="formGroupPhone">
                <Form.Label>Phone Number</Form.Label>
                <Form.Control
                  readOnly
                  defaultValue={loggedInStudent.phoneNumber}
                />
              </Form.Group>
              <Form.Group className="mb-3" controlId="formGroupFaculty">
                <Form.Label>Faculty</Form.Label>
                <Form.Control readOnly defaultValue={loggedInStudent.faculty} />
              </Form.Group>
              <Form.Group className="mb-3" controlId="formGroupEmail">
                <Form.Label>Partner University</Form.Label>
                <InputGroup>
                  <Form.Control
                    readOnly
                    defaultValue={loggedInStudent.partnerUniversity}
                  />
                  <Button>
                    <FontAwesomeIcon icon={faPenToSquare} /> Add Review
                  </Button>
                </InputGroup>
              </Form.Group>
            </Form>
          </div>
        </Col>
      </Row>
      <React.Fragment>
        <br />
        <Button variant="secondary">Liked PUS</Button>
      </React.Fragment>
    </div>
  );
};

export default StudentProfile;
