import React from "react";
import { useContext, useState, useEffect } from "react";
import { AuthContext } from "../login/AuthContext";
import { useNavigate } from "react-router-dom";

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

// Icon imports
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faPenToSquare,
  faPen,
  faHeart,
  faChevronRight,
} from "@fortawesome/free-solid-svg-icons";

import SocialMediaModal from "./SocialMediaModal";
import IconSocialMedia from "./IconSocialMedia";
import defaultProfilePicture from "../images/housekeeper.png";

const StudentProfile = () => {
  const { loggedInStudent } = useContext(AuthContext);
  //const { studentId } = loggedInStudent;
  const API_URL_STUDENT = "http://localhost:8080/PU-war/webresources/student";
  const [currentStudent, setCurrentStudent] = useState({ ...loggedInStudent });
  const [puEnrolled, setPuEnrolled] = useState({
    name: "Dummy Uni",
    puId: 0
  });
  const [socialMedia, setSocialMedia] = useState([]);
  const [reviewModalShow, setReviewModalShow] = useState(false);
  const [socialMediaModalShow, setSocialMediaModalShow] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    if (loggedInStudent) {
      getStudentAPI(loggedInStudent.studentId);
      getEnrolledPuAPI(loggedInStudent.studentId);
    }
  }, [loggedInStudent]);

  const getStudentAPI = async (studentId) => {
    const response = await fetch(`${API_URL_STUDENT}/${studentId}`);
    const data = await response.json();
    setCurrentStudent(data);
    setSocialMedia(data.socialMedia);
  };

  const getEnrolledPuAPI = async (studentId) => {
    const response = await fetch(`${API_URL_STUDENT}/${studentId}/puEnrolled`);
    const data = await response.json();
    console.log(data);
    setPuEnrolled(data);
  };

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
        <div>
          <InputGroup>
            <Form.Control
              readOnly
              value={puEnrolled.name}
            />
            <Button onClick={() => setReviewModalShow(true)}>
              <FontAwesomeIcon icon={faPenToSquare} />
              {currentStudent.puReview != null ? " Edit Review" : " Add Review"}
            </Button>
          </InputGroup>

          <Button variant="link" onClick={navToModulesTaken}>
            View Modules Taken {"  "}
            <FontAwesomeIcon icon={faChevronRight} size="2xs" />
          </Button>
        </div>
      );
    } else {
      return (
        <InputGroup>
          <Form.Control readOnly defaultValue="Unenrolled" />
        </InputGroup>
      );
    }
  };

  const handleSocialMediaChange = (newSocialMedia) => {
    setSocialMedia(newSocialMedia);
  };

  const navToLikedPUs = () => {
    navigate("/profile/likedPus");
  };

  const navToModulesTaken = () => {
    navigate("/profile/modulesTaken");
  };

  return (
    <div style={{ paddingLeft: "5%", paddingRight: "5%" }}>
      <div className="container" style={{ border: 0 }}>
        <h1 className="text-center mb-3">Your Profile</h1>
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
                Last Active: {loggedInStudent.lastActive}
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
                    You do not have any social media links. Add them now!
                  </ListGroupItem>
                )}
              </ListGroup>
              <Card.Footer style={{ textAlign: "center" }}>
                <Button
                  variant="link"
                  onClick={() => setSocialMediaModalShow(true)}
                >
                  <FontAwesomeIcon icon={faPen} /> {"  "}
                  Edit Links
                </Button>
              </Card.Footer>
            </Card>
            <div className="d-grid gap-2">
              <Button variant="danger" onClick={navToLikedPUs}>
                <FontAwesomeIcon icon={faHeart} /> {"  "}
                View Liked Universities
              </Button>
            </div>
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
                  isEnrolled={puEnrolled.puId !== 0}
                />
              </Form.Group>
            </Form>
          </div>
        </Col>
      </Row>

      <ReviewModal
        show={reviewModalShow}
        onHide={() => setReviewModalShow(false)}
        puReview={currentStudent.puReview}
        studentId={currentStudent.studentId}
      />
      <SocialMediaModal
        show={socialMediaModalShow}
        onHide={() => setSocialMediaModalShow(false)}
        socialMedia={socialMedia}
        onSocialMediaChange={handleSocialMediaChange}
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

                <ListGroupItem>
                      <a href={socialMedia}>{socialMedia}</a>
                    </ListGroupItem>

*/
