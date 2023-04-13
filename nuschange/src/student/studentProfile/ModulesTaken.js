import React, { useContext, useEffect, useState } from "react";
import { Button, Card, ListGroup, ListGroupItem } from "react-bootstrap";
import { Link, useNavigate } from "react-router-dom";
import { AuthContext } from "../login/AuthContext";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPenToSquare } from "@fortawesome/free-solid-svg-icons";
import ModReviewModal from "./ModReviewModal";

const ModulesTaken = () => {
  //const navigate = useNavigate();
  const API_URL_STUDENT = "http://localhost:8080/PU-war/webresources/student";
  const { loggedInStudent } = useContext(AuthContext);
  const [reviewModModalShow, setReviewModModalShow] = useState(false);
  const [puEnrolled, setPuEnrolled] = useState({
    name: "Dummy Uni",
    puId: 0
  });

  useEffect(() => {
    if (loggedInStudent) {
      getEnrolledPuAPI(loggedInStudent.studentId);
    }
  }, [loggedInStudent]);


  const getEnrolledPuAPI = async (studentId) => {
    const response = await fetch(`${API_URL_STUDENT}/${studentId}/puEnrolled`);
    const data = await response.json();
    //console.log(data);
    setPuEnrolled(data);
  };

  const dummyModules = [
    {
      code: "CS101",
      description: "This module covers the basics of programming with Java.",
      moduleId: 1,
      moduleReviews: [],
      name: "Introduction to Programming",
    },
    {
      code: "CS201",
      description:
        "This module explores various data structures and algorithms used in programming.",
      moduleId: 2,
      moduleReviews: [],
      name: "Data Structures and Algorithms",
    },
    {
      code: "CS301",
      description:
        "This module covers the design and implementation of database systems.",
      moduleId: 3,
      moduleReviews: [],
      name: "Database Systems",
    },
    {
      code: "CS401",
      description:
        "This module covers the fundamentals of artificial intelligence and machine learning.",
      moduleId: 4,
      moduleReviews: [],
      name: "Artificial Intelligence",
    },
    {
      code: "CS501",
      description:
        "This module covers the concepts and protocols used in computer networks.",
      moduleId: 5,
      moduleReviews: [],
      name: "Computer Networks",
    },
  ];

  return (
    <div style={{ paddingLeft: "5%", paddingRight: "5%" }}>
      <div className="container" style={{ border: 0 }}>
        <h1 className="text-center mb-3">Modules Taken</h1>
      </div>
      <Card>
        <Card.Header as="h4">Modules</Card.Header>
        <ListGroup>
          {dummyModules.map((mod) => (
            <ListGroup.Item
              as="li"
              className="d-flex justify-content-between align-items-start"
              action
            >
              <div className="ms-2 me-auto">
                <Link
                  to={`/module-details/${puEnrolled.name}/${mod.moduleId}`}
                  style={{ color: "black", textDecoration: "none" }}
                >
                  <div className="fw-bold">
                    <h5>{mod.code}</h5>
                  </div>
                  {mod.name}
                </Link>
              </div>
              <Button onClick={() => setReviewModModalShow(true)}>
                <FontAwesomeIcon icon={faPenToSquare} />
                Add Review
              </Button>
            </ListGroup.Item>
          ))}

          {loggedInStudent.modulesTaken.length > 0 &&
            loggedInStudent.modulesTaken.map((mod) => (
              <ListGroup.Item
                as="li"
                className="d-flex justify-content-between align-items-start"
                action
              >
                <div className="ms-2 me-auto">
                  <Link
                    to={`/module-details/${puEnrolled.name}/${mod.moduleId}`}
                    style={{ color: "black", textDecoration: "none" }}
                  >
                    <div className="fw-bold">
                      <h5>{mod.code}</h5>
                    </div>
                    {mod.name}
                  </Link>
                </div>
                <Button onClick={() => setReviewModModalShow(true)}>
                  <FontAwesomeIcon icon={faPenToSquare} />
                  Add Review
                </Button>
              </ListGroup.Item>
            ))}

          {loggedInStudent.modulesTaken.length === 0 && (
            <ListGroupItem>
              You have not been assigned to any Modules!
            </ListGroupItem>
          )}
        </ListGroup>
      </Card>
      <ModReviewModal
        show={reviewModModalShow}
        onHide={() => setReviewModModalShow(false)}
      />
    </div>
  );
};

export default ModulesTaken;
