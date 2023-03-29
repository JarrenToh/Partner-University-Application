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
} from "react-bootstrap";
import ReviewModal from "./ReviewModal";

const StudentProfile = () => {
  const { loggedInStudent, logout } = useContext(AuthContext);
  const [reviewModalShow, setReviewModalShow] = useState(false);
  
  if (!loggedInStudent) {
    return <div> Not Logged in</div>;
  }

  // Concat first and last name to make full name
  const studentFullName =
    loggedInStudent.firstName + " " + loggedInStudent.lastName;

  // Component to show relevant information 
  // depending on whether student is enrolled to a PU
  const EnrolledField = (props) => {
    const isEnrolled = props.isEnrolled;

    if (isEnrolled) {
      return (
        <InputGroup>
          <Form.Control
            readOnly
            defaultValue={loggedInStudent.partnerUniversity}
          />
          <Button onClick={() => setReviewModalShow(true)}>
            <FontAwesomeIcon icon={faPenToSquare} /> Add Review
          </Button>
        </InputGroup>
      );
    } else {
      return (
        <InputGroup>
          <Form.Control readOnly defaultValue="Unenrolled" />
        </InputGroup>
      );
    }
  };

  return (
    <div style={{ paddingLeft: "5%", paddingRight: "5%" }}>
      <div className="container">
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
                <EnrolledField
                  isEnrolled={
                    loggedInStudent.partnerUniversity == null
                  }
                />
              </Form.Group>
            </Form>
          </div>
        </Col>
      </Row>
      <React.Fragment>
        <br />
        <Button variant="secondary">Liked PUS</Button>
      </React.Fragment>

      <ReviewModal
       show={reviewModalShow}
       onHide={() => setReviewModalShow(false)}
      />

    </div>
  );
};

export default StudentProfile;


/*
  {loggedInStudent.social.map((socialMedia) => (
                  <ListGroupItem>

                  </ListGroupItem>
                ))}

*/