import React, { useContext } from "react";
import { Card, ListGroup, ListGroupItem } from "react-bootstrap";
import { Link } from "react-router-dom";
import { AuthContext } from "../login/AuthContext";


const ModulesTaken = () => {
  const { loggedInStudent } = useContext(AuthContext);

  return (
    <div style={{ paddingLeft: "5%", paddingRight: "5%" }}>
      <div className="container" style={{border: 0}}>
      <h1>Modules Taken</h1>
      </div>
      <Card>
              <Card.Header as="h4">Modules</Card.Header>
      <ListGroup>
        {loggedInStudent.modulesTaken.length > 0 &&
          loggedInStudent.modulesTaken.map((module) => (
            <ListGroupItem>{module}</ListGroupItem>
          ))}

        {loggedInStudent.modulesTaken.length == 0 && (
          <ListGroupItem>
            You have not been assigned to any Modules!
          </ListGroupItem>
        )}
        
      </ListGroup>
      </Card>

      <Link to="../module-reviews">View All Module Reviews</Link>
    </div>
  );
};

export default ModulesTaken;
