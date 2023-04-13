import "../assets/base.scss";
import "bootstrap/dist/css/bootstrap.min.css";
import "./AccordionComp.css";
import React from "react";
import PerfectScrollbar from "react-perfect-scrollbar";
import { Card, ListGroup, ListGroupItem, CardHeader } from "reactstrap";
import defaultProfilePicture from "../images/housekeeper.png";
import { Link } from "react-router-dom";

function AlumnusComp(props) {
  return (
    <Card className="card-box mb-5">
      <CardHeader style={{ textAlign: "center" }}>
        <h4 className="font-size-lg mb-0 py-2 font-weight-bold">Alumnus</h4>
      </CardHeader>
      <ListGroup flush>
        <div
          className="scroll-area rounded bg-white shadow-overflow"
          style={{ width: "100%", overflowX: "auto" }}
        >
          {props.alumnus.length === 0 ? (
            <div className="text-center py-5">
              <h4 className="text-muted">No Alumni Found</h4>
            </div>
          ) :
            <PerfectScrollbar>
              {props.alumnus.map((alumni) => (
                <>
                  <ListGroupItem className="py-3 border-0" key={alumni.studentId}>
                    <Link
                      to={`/other-profile/${alumni.studentId}`}
                      style={{ color: "black", textDecoration: "none" }}
                    >
                      <div className="align-box-row w-100">
                        <div className="mr-2">
                          <img
                            src={defaultProfilePicture}
                            alt="Default Profile"
                            className="rounded-circle"
                            style={{ width: "40px", height: "40px" }}
                          />
                        </div>

                        <div>
                          <div className="font-weight-bold d-block opacity-8">
                            {alumni.firstName} {alumni.lastName}
                          </div>
                          <div
                            className="text-dark opacity-50"
                            style={{ color: "#111111" }}
                          >
                            <a href={`mailto:${alumni.email}`}>{alumni.email}</a>
                          </div>
                        </div>
                      </div>
                    </Link>
                  </ListGroupItem>
                  <div className="divider" />
                </>
              ))}
            </PerfectScrollbar>
          }
        </div>
      </ListGroup>
    </Card>
  );
}

export default AlumnusComp;
