import React from "react";
import { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";

import {
  Image,
  Col,
  Form,
  Row,
  InputGroup,
  ListGroup,
  ListGroupItem,
  Card,
} from "react-bootstrap";

import defaultProfilePicture from "../images/housekeeper.png";
import IconSocialMedia from "../studentProfile/IconSocialMedia";

const OtherStudentProfile = () => {
  const { studentId } = useParams();
  const API_URL_STUDENT = "http://localhost:8080/PU-war/webresources/student";
  const [currentStudent, setCurrentStudent] = useState({
    email: "jessicawong@comp.nus.edu.sg", 
    firstName: "Dummy",
    lastName: "Student",
    moduleReviews: [],
    modulesTaken: [],
    phoneNumber: "90002047",
    puReview: {
      isInappropriate: false,
      noOfDislikes: 0,
      noOfLikes: 0,
      puReviewId: 4,
      rating: 4,
      review:
        "This university provided me with an exceptional education and countless opportunities to get involved on campus. I would highly recommend it.",
    },
    socialMedia: [],
    studentId: 0,
  });
  const [socialMedia, setSocialMedia] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    console.log(studentId);
    getStudentAPI(studentId);
  }, []);

  const getStudentAPI = async (studentId) => {
    const response = await fetch(`${API_URL_STUDENT}/${studentId}`);
    const data = await response.json();
    console.log(data);
    setCurrentStudent(data);
    setSocialMedia(data.socialMedia);
  };

  // Concat first and last name to make full name
  const studentFullName =
    currentStudent.firstName + " " + currentStudent.lastName;

  // Component to show relevant information
  // depending on whether student is enrolled to a PU
  const EnrolledField = (props) => {
    const isEnrolled = props.isEnrolled;
    console.log(isEnrolled);
    if (isEnrolled) {
      return (
        <div>
          <InputGroup>
            <Form.Control
              readOnly
              value={currentStudent.puEnrolled}
            />
          </InputGroup>
        </div>
      );
    } else {
      return (
        <InputGroup>
          <Form.Control readOnly value="Unenrolled" />
        </InputGroup>
      );
    }
  };


  return (
    <div style={{ paddingLeft: "5%", paddingRight: "5%" }}>
      <div className="container" style={{ border: 0 }}>
        <h1 className="text-center mb-3">{currentStudent.firstName}'s Profile</h1>
      </div>
      <Row>
        <Col>
          <div id="profilePicDiv" style={{ padding: "5%" }}>
            <Card>
              <Card.Body style={{ padding: "5%", textAlign: "center" }}>
                <Image
                  src={defaultProfilePicture}
                  roundedCircle
                  style={{ width: "200px", height: "200px" }}
                />
              </Card.Body>
              <Card.Footer>
                Last Active: {currentStudent.lastActive}
              </Card.Footer>
            </Card>
            <Card>
              <Card.Header as="h5">Social Media</Card.Header>
              <ListGroup variant="flush">
                {socialMedia != null &&
                  socialMedia.map((link) => (
                    <ListGroupItem>
                      <IconSocialMedia linkType={link} /> {"   "}
                      <a
                        href={`https://${link}`}
                        target="_blank"
                        rel="noopener noreferrer"
                      >
                        {link}
                      </a>
                    </ListGroupItem>
                  ))}

                {socialMedia.length === 0 && (
                  <ListGroupItem>
                    This user has not added any social media links.
                  </ListGroupItem>
                )}
              </ListGroup>
            </Card>
          </div>
        </Col>

        <Col>
          <div id="formDiv" style={{ padding: "5%" }}>
            <Form>
              <Form.Group className="mb-3" controlId="formGroupName">
                <Form.Label>Full Name</Form.Label>
                <Form.Control readOnly value={studentFullName} />
              </Form.Group>
              <Form.Group className="mb-3" controlId="formGroupEmail">
                <Form.Label>Email</Form.Label>
                <Form.Control readOnly value={currentStudent.email} />
              </Form.Group>
              <Form.Group className="mb-3" controlId="formGroupPhone">
                <Form.Label>Phone Number</Form.Label>
                <Form.Control
                  readOnly
                  value={currentStudent.phoneNumber}
                />
              </Form.Group>
              <Form.Group className="mb-3" controlId="formGroupFaculty">
                <Form.Label>Faculty</Form.Label>
                <Form.Control readOnly value={currentStudent.faculty} />
              </Form.Group>
              <Form.Group className="mb-3" controlId="formGroupEmail">
                <Form.Label>Partner University</Form.Label>
                <EnrolledField
                  isEnrolled={currentStudent.puEnrolled != null}
                />
              </Form.Group>
            </Form>
          </div>
        </Col>
      </Row>
    </div>
  );
};

export default OtherStudentProfile;

/*
  {currentStudent.social.map((socialMedia) => (
                  <ListGroupItem>

                  </ListGroupItem>
                ))}

                <ListGroupItem>
                      <a href={socialMedia}>{socialMedia}</a>
                    </ListGroupItem>

*/
